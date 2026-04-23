package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.PrecioDTO;
import com.gamm.vinos_api.repository.PrecioRepository;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.service.PrecioService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrecioServiceImpl extends BaseService implements PrecioService {

  private final PrecioRepository precioRepository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  @Override
  public ResultadoSP asignarPrecio(PrecioSucursal precio) {

    Integer idSucursal;

    if (esAdministrador()) {
      if (precio.getSucursal() == null || precio.getSucursal().getIdSucursal() == null) {
        throw new IllegalStateException("El administrador debe indicar sucursal");
      }
      idSucursal = precio.getSucursal().getIdSucursal();
    } else {
      idSucursal = getIdSucursalAutenticada();
    }

    if (precio.getSucursal() == null) {
      precio.setSucursal(new Sucursal());
    }
    precio.getSucursal().setIdSucursal(idSucursal);

    ResultadoSP resultado = precioRepository.asignarPrecio(precio);
    ResponseVO.validar(resultado);

    webSocketService.notifyPrecioUpdate();
    String nombreVino = precioRepository.obtenerNombreVino(precio.getVino().getIdVino());

    if (esAdministrador()) {
      notificacionService.notificarRolYSucursal(
          "Vendedor",
          idSucursal,
          "INFO",
          "Precio asignado a tu stock",
          "El administrador ha asignado un precio de S/." + precio.getPrecioVenta()
              + " a " + nombreVino + " de tu stock.",
          "/ventas/establecer-precios"
      );
    } else {
      notificacionService.notificarRol(
          "Administrador",
          "INFO",
          "Precio asignado por vendedor",
          getNombreUsuarioAutenticado()
              + " ha asignado un precio de S/." + precio.getPrecioVenta()
              + " a " + nombreVino
              + " de su stock en " + getNombreSucursalAutenticado() + ".",
          "/ventas/establecer-precios"
      );
    }
    return resultado;
  }

  @Override
  public List<PrecioDTO> listarTotalPreciosStock() {
    return precioRepository.listarTotalPreciosStock();
  }

  @Override
  public List<PrecioDTO> listarPreciosStockSucursal() {
    return precioRepository.listarPreciosStockSucursal(getIdSucursalAutenticada());
  }

  @Override
  public ResultadoSP filtrarPorVinoOSucursal(String nombreVino, Integer idSucursal) {
    return precioRepository.filtrarPorVinoOSucursal(nombreVino, idSucursal);
  }

  @Override
  public List<PrecioDTO> listarPreciosDetalle(Integer idVino, Integer idSucursal) {
    return precioRepository.listarPreciosDetalle(idVino, idSucursal);
  }

  @Override
  public ResultadoSP filtrarPorVino(String nombreVino) {
    return precioRepository.filtrarPorVino(nombreVino, getIdSucursalAutenticada());
  }

}
