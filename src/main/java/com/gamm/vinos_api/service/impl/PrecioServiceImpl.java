package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.Persona;
import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.view.PrecioView;
import com.gamm.vinos_api.repository.PrecioRepository;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.service.PrecioService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrecioServiceImpl implements PrecioService {

  private final PrecioRepository precioRepository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  /* Helpers */
  private Integer getUsuarioAutenticado() {
    Integer idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) {
      throw new IllegalStateException("Usuario no logueado");
    }
    return idUsuario;
  }

  private Integer getIdSucursalAutenticada() {
    Integer idSucursal = SecurityUtils.getSucursalId();
    if (idSucursal == null) {
      throw new IllegalStateException("El usuario no tiene sucursal asignada");
    }
    return idSucursal;
  }

  @Override
  public ResultadoSP asignarPrecio(PrecioSucursal precio) {

    Integer idUsuario = getUsuarioAutenticado();

    Usuario usuario = new Usuario();
    usuario.setIdUsuario(idUsuario);
    Persona persona = new Persona();
    persona.setIdPersona(idUsuario);

    String rol = SecurityUtils.getRol();

    Integer idSucursal;

    if ("ADMINISTRADOR".equalsIgnoreCase(rol)) {
      // Admin puede elegir sucursal
      if (precio.getSucursal() == null || precio.getSucursal().getIdSucursal() == null) {
        throw new IllegalStateException("El administrador debe indicar sucursal");
      }
      idSucursal = precio.getSucursal().getIdSucursal();

    } else {
      // Vendedor usa su sucursal autenticada
      idSucursal = getIdSucursalAutenticada();
    }

    if (precio.getSucursal() == null) {
      precio.setSucursal(new Sucursal());
    }

    precio.getSucursal().setIdSucursal(idSucursal);

    ResultadoSP resultado = precioRepository.asignarPrecio(precio);

    if (resultado.esExitoso()) {
      webSocketService.notifyPrecioUpdate();
      // Obtener el nombre del vino
      String nombreVino = precioRepository.obtenerNombreVino(precio.getVino().getIdVino());
      String nombreCompleto = SecurityUtils.getNombreCompleto();
      if ("ADMINISTRADOR".equalsIgnoreCase(rol)) {
        // Notificar solo al vendedor de esa sucursal
        notificacionService.notificarRolYSucursal(
            "Vendedor",
            idSucursal,
            "INFO",
            "Precio asignado a tu stock",
            "El administrador ha asignado un precio de S/." + precio.getPrecioVenta() + " a " + nombreVino + " de tu stock.",
            "/ventas/establecer-precios"
        );
      } else {
        // Vendedor asignó su precio - notificar al administrador
        notificacionService
            .notificarRol(
                "Administrador",
                "INFO",
                "Precio asignado por vendedor",
                nombreCompleto + " ha asignado un precio de S/." + precio.getPrecioVenta() + " a " + nombreVino + " de su stock.",
                "/ventas/establecer-precios"
            );
      }
    }
    return resultado;
  }

  @Override
  public List<PrecioView> listarTotalPreciosStock() {
    return precioRepository.listarTotalPreciosStock();
  }

  @Override
  public List<PrecioView> listarPreciosStockSucursal() {
    return precioRepository.listarPreciosStockSucursal(getIdSucursalAutenticada());
  }

  @Override
  public ResultadoSP filtrarPorVinoOSucursal(String nombreVino, Integer idSucursal) {
    return precioRepository.filtrarPorVinoOSucursal(nombreVino, idSucursal);
  }

  @Override
  public List<PrecioView> listarPreciosDetalle(Integer idVino, Integer idSucursal) {
    return precioRepository.listarPreciosDetalle(idVino, idSucursal);
  }

  @Override
  public ResultadoSP filtrarPorVino(String nombreVino) {
    return precioRepository.filtrarPorVino(nombreVino, getIdSucursalAutenticada());
  }

}
