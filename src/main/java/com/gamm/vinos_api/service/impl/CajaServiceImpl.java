package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.view.CajaView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.CajaRepository;
import com.gamm.vinos_api.service.BaseService;
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

  @Override
  public ResultadoSP abrirCaja(Caja caja) {

    Integer idUsuario = getIdUsuarioAutenticado();

    Usuario usuario = new Usuario();
    usuario.setIdUsuario(idUsuario);
    caja.setUsuario(usuario);

    Sucursal sucursal = new Sucursal();

    if (esAdministrador()) {
      if (caja.getSucursal() == null || caja.getSucursal().getIdSucursal() == null) {
        return new ResultadoSP(0, "Debe seleccionar una sucursal");
      }
      sucursal.setIdSucursal(caja.getSucursal().getIdSucursal());
    } else {
      Integer idSucursal = getIdSucursalAutenticada();
      if (idSucursal <= 0) {
        return new ResultadoSP(0, "No tienes una sucursal asignada.\nContacta al administrador.");
      }
      sucursal.setIdSucursal(idSucursal);
    }
    caja.setSucursal(sucursal);

    ResultadoSP resultado = cajaRepository.abrirCaja(caja);

    if (!resultado.esExitoso()) {
      return resultado;
    }

    webSocketService.notifyCajaUpdate();
    webSocketService.notifyCompraUpdate();
    webSocketService.notifyPrecioUpdate();

    CajaView cajaAbierta = cajaRepository.obtenerUltimaCajaAbiertaUsuario(idUsuario);
    String codCaja = cajaAbierta != null ? cajaAbierta.getCodCaja() : "";

    if (esAdministrador()) {
      notificacionService.notificarRolYSucursal(
          "Vendedor",
          caja.getSucursal().getIdSucursal(),
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
    if (!resultado.esExitoso()) return resultado;

    webSocketService.notifyCajaUpdate();
    webSocketService.notifyCompraUpdate();
    webSocketService.notifyPrecioUpdate();

    CajaView caja = cajaRepository.obtenerCajaPorId(idCaja);
    if (caja == null)  throw new IllegalStateException("Caja no encontrada");

    if (esAdministrador()) {
      notificacionService.notificarRolYSucursal(
          "Vendedor",
          caja.getIdSucursal(),
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
  public ResponseVO listarMisCajas(int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    List<CajaView> data = cajaRepository.listarMisCajas(idUsuario, pagina, limite);

    long totalRegistros = cajaRepository.contarMisCajas(idUsuario);
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO listarTotalCajas(int pagina, int limite) {

    long totalRegistros = cajaRepository.contarTotalCajas();
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);
    List<CajaView> data = cajaRepository.listarTotalCajas(pagina, limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO filtrarMisCajasPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();

    ResultadoSP resultadoSP = cajaRepository.filtrarMisCajasPorRango(idUsuario, fechaInicio, fechaFin, pagina, limite);

    if (!resultadoSP.esExitoso()) {
      return ResponseVO.error(resultadoSP.getMensaje());
    }

    @SuppressWarnings("unchecked")
    List<CajaView> data = (List<CajaView>) resultadoSP.getData();

    long totalRegistros = cajaRepository.contarMisCajasPorRango(idUsuario, fechaInicio, fechaFin);
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);

  }

  @Override
  public ResponseVO filtrarCajasPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    ResultadoSP resultadoSP = cajaRepository.filtrarTotalCajasPorRango(idUsuario, fechaInicio, fechaFin, pagina, limite);

    if (!resultadoSP.esExitoso()) {
      return ResponseVO.error(resultadoSP.getMensaje());
    }

    @SuppressWarnings("unchecked")
    List<CajaView> data = (List<CajaView>) resultadoSP.getData();

    long totalRegistros;
    if (idUsuario != null) {
      totalRegistros = cajaRepository.contarTotalCajasPorRango(idUsuario, fechaInicio, fechaFin);
    } else {
      totalRegistros = cajaRepository.contarTotalCajasPorRango(null, fechaInicio, fechaFin);
    }

    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public List<CajaView> mostrarMiUltimaCajaAbierta() {
    Integer idUsuario = getIdUsuarioAutenticado();
    return cajaRepository.mostrarMiUltimaCajaAbierta(idUsuario);
  }

  @Override
  public ResultadoSP obtenerSiguienteCodigoCaja() {
    Integer idSucursal = getIdSucursalAutenticada();
    return cajaRepository.obtenerSiguienteCodigoCaja(idSucursal);
  }
}
