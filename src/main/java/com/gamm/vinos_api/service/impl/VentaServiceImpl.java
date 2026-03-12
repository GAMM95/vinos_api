package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.VentaRepository;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.VentaService;
import com.gamm.vinos_api.util.ResultadoSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

  @Autowired
  private VentaRepository ventaRepository;

  /* helpers */
  private Integer getUsuarioAutenticado() {
    Integer idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) {
      throw new IllegalStateException("Usuario no encontrado");
    }
    return idUsuario;
  }

  // Metodo para lsitar detalles de venta para el usuario y administrador
  private ResponseVO listarDetalleVenta(Integer idVenta, Integer idUsuario) {
    List<CarritoVentaView> detalles =
        idUsuario != null
            ? ventaRepository.listarCarritoVentaUsuario(idUsuario)
            : ventaRepository.listarCarritoVentaAdmin(idVenta);
    return ResponseVO.success(detalles);
  }

  @Override
  public List<CarritoVentaView> listarCarritoVentaUsuario() {
    Integer idUsuario = getUsuarioAutenticado();
    return ventaRepository.listarCarritoVentaUsuario(idUsuario);
  }

  @Override
  public List<CarritoVentaView> listarCarritoVentaAdmin(Integer idVenta) {
    return ventaRepository.listarCarritoVentaAdmin(idVenta);
  }

  @Override
  public ResultadoSP agregarCarritoVenta(Venta venta) {
    Integer idUsuario = getUsuarioAutenticado();
    DetalleVenta detalle = venta.getDetalles().stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Debe enviar un producto"));

    return ventaRepository.agregarProductoCarrito(idUsuario, venta, detalle);
  }
}
