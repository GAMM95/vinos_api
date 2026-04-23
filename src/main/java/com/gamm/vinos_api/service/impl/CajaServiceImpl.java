package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.CajaDTO;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.exception.business.BusinessException;
import com.gamm.vinos_api.repository.CajaRepository;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.service.CajaService;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CajaServiceImpl extends BaseService implements CajaService {

  private final CajaRepository cajaRepository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  // ─── Operaciones ──────────────────────────────────────────────────────────

  @Override
  public ResultadoSP abrirCaja(Caja caja) {
    Integer idUsuario = getIdUsuarioAutenticado();
    Usuario usuario = new Usuario();
    usuario.setIdUsuario(idUsuario);
    caja.setUsuario(usuario);

    Sucursal sucursal = new Sucursal();

    if (esAdministrador()) {
      if (caja.getSucursal() == null || caja.getSucursal().getIdSucursal() == null) {
        throw new BusinessException("Debe seleccionar una sucursal.");
      }
      sucursal.setIdSucursal(caja.getSucursal().getIdSucursal());
    } else {
      Integer idSucursal = getIdSucursalAutenticada();
      sucursal.setIdSucursal(idSucursal);
    }
    caja.setSucursal(sucursal);

    ResultadoSP resultado = cajaRepository.abrirCaja(caja);
    ResponseVO.validar(resultado);

    webSocketService.notifyCajaUpdate();
    webSocketService.notifyCompraUpdate();
    webSocketService.notifyPrecioUpdate();

    CajaDTO cajaAbierta = cajaRepository.obtenerUltimaCajaAbiertaUsuario(idUsuario);
    String codCaja = cajaAbierta != null ? cajaAbierta.getCodCaja() : "";

    if (esAdministrador()) {
      notificacionService.notificarRolYSucursal(
          "Vendedor", caja.getSucursal().getIdSucursal(),
          "INFO",
          "Caja abierta",
          "El administrador abrió la caja " + codCaja + " en tu sucursal.",
          "/caja/mis-cajas"
      );
    } else {
      notificacionService.notificarRol(
          "Administrador",
          "INFO",
          "Caja abierta",
          getNombreUsuarioAutenticado() + " abrió la caja " + codCaja + " en la sucursal " + getNombreSucursalAutenticado(),
          "/caja/consultar-caja"
      );
    }

    return resultado;
  }

  @Override
  public ResultadoSP cerrarCaja(Integer idCaja) {
    ResultadoSP resultado = cajaRepository.cerrarCaja(idCaja);
    ResponseVO.validar(resultado);

    webSocketService.notifyCajaUpdate();
    webSocketService.notifyCompraUpdate();
    webSocketService.notifyPrecioUpdate();

    CajaDTO caja = cajaRepository.obtenerCajaPorId(idCaja);
    if (caja == null) throw new BusinessException("Caja no encontrada.");

    if (esAdministrador()) {
      notificacionService.notificarRolYSucursal(
          "Vendedor", caja.getIdSucursal(),
          "WARNING",
          "Caja cerrada",
          "El administrador cerró la caja " + caja.getCodCaja() + " de tu sucursal",
          "/caja/mis-cajas"
      );

    }
    // VENDEDOR → admin global
    else {
      notificacionService.notificarRol(
          "Administrador",
          "WARNING",
          "Caja cerrada",
          caja.getUsuario() + " cerró la caja " + caja.getCodCaja() + " en la sucursal " + caja.getSucursal(),
          "/caja/consultar-caja"
      );
    }
    return resultado;
  }

  @Override
  public ResultadoSP obtenerSiguienteCodigoCaja() {
    Integer idSucursal = getIdSucursalAutenticada();
    return cajaRepository.obtenerSiguienteCodigoCaja(idSucursal);
  }

  // ─── Consultas del usuario autenticado ────────────────────────────────────

  @Override
  public List<CajaDTO> mostrarMiUltimaCajaAbierta() {
    Integer idUsuario = getIdUsuarioAutenticado();
    return cajaRepository.mostrarMiUltimaCajaAbierta(idUsuario);
  }

  @Override
  public PaginaResultado<CajaDTO> listarMisCajas(int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    List<CajaDTO> data = cajaRepository.listarMisCajas(idUsuario, pagina, limite);
    long total = cajaRepository.contarMisCajas(idUsuario);
    return construirPagina(data, pagina, limite,total);
  }


  @Override
  public PaginaResultado<CajaDTO> filtrarMisCajasPorRango(
      LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite
  ) {
    Integer idUsuario = getIdUsuarioAutenticado();
    ResultadoSP resultado = cajaRepository.filtrarMisCajasPorRango(
        idUsuario, fechaInicio, fechaFin, pagina, limite
    );
    ResponseVO.validar(resultado);

    @SuppressWarnings("unchecked")
    List<CajaDTO> data = (List<CajaDTO>) resultado.getData();
    long total = cajaRepository.contarMisCajasPorRango(idUsuario, fechaInicio, fechaFin);

    return construirPagina(data, pagina, limite,total);
  }

  // ─── Administración ───────────────────────────────────────────────────────

  @Override
  public PaginaResultado<CajaDTO> listarTotalCajas(int pagina, int limite) {
    List<CajaDTO> data = cajaRepository.listarTotalCajas(pagina, limite);
    long total = cajaRepository.contarTotalCajas();
    return construirPagina(data, pagina, limite,total);
  }


  @Override
  public PaginaResultado<CajaDTO> filtrarCajasPorUsuarioORango(
      Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin,
      int pagina, int limite
  ) {
    ResultadoSP resultado = cajaRepository.filtrarTotalCajasPorRango(
        idUsuario, fechaInicio, fechaFin, pagina, limite
    );
    ResponseVO.validar(resultado);

    @SuppressWarnings("unchecked")
    List<CajaDTO> data = (List<CajaDTO>) resultado.getData();
    long total = cajaRepository.contarTotalCajasPorRango(idUsuario, fechaInicio, fechaFin);
    return construirPagina(data, pagina, limite,total);
  }

}
