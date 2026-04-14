package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.MovimientosView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.MovimientoRepository;
import com.gamm.vinos_api.service.BaseService;
import com.gamm.vinos_api.service.MovimientoService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientosServiceImpl extends BaseService implements MovimientoService {

  private final MovimientoRepository movimientoRepository;

  /* Helpers */
  private ResponseVO listarDetalleMovimiento(Integer idCaja, Integer idUsuario) {
    List<MovimientosView> data = idUsuario != null
        ? movimientoRepository.listarDetalleMovimientoUsuario(idUsuario, idCaja)
        : movimientoRepository.listarDetalleMovimiento(idCaja);
    return ResponseVO.success(data);
  }

  @Override
  public ResponseVO filtrarMisMovimientosPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    ResultadoSP resultadoSP = movimientoRepository.filtrarMisMovimientosPorRango(idUsuario, fechaInicio, fechaFin, pagina, limite);

    if (!resultadoSP.esExitoso()) {
      return ResponseVO.error(resultadoSP.getMensaje());
    }

    @SuppressWarnings("unchecked")
    List<MovimientosView> data = (List<MovimientosView>) resultadoSP.getData();

    long totalRegistros = movimientoRepository.contarMisMovimientosPorRango(idUsuario, fechaInicio, fechaFin);
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO filtrarMovimientosPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    ResultadoSP resultadoSP = movimientoRepository.filtrarMovimientosPorUsuarioORango(idUsuario, fechaInicio, fechaFin, pagina, limite);

    if (!resultadoSP.esExitoso()) {
      return ResponseVO.error(resultadoSP.getMensaje());
    }

    @SuppressWarnings("unchecked")
    List<MovimientosView> data = (List<MovimientosView>) resultadoSP.getData();

    long totalRegistros = movimientoRepository.contarMovimientosPorRango(idUsuario, fechaInicio, fechaFin);
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO listarMisMovimientos(int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    List<MovimientosView> data = movimientoRepository.listarMisMovimientos(idUsuario, pagina, limite);

    long totalRegistros = movimientoRepository.contarMisMovimientos(idUsuario);
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO listarTotalMovimientos(int pagina, int limite) {
    long totalRegistros = movimientoRepository.contarTotalMovimientos();
    List<MovimientosView> data = movimientoRepository.listarTotalMovimientos(pagina, limite);

    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO listarDetalleMovimientoUsuario(Integer idCaja) {
    return listarDetalleMovimiento(idCaja, getIdUsuarioAutenticado());
  }

  @Override
  public ResponseVO listarDetalleMovimientoAdmin(Integer idCaja) {
    return listarDetalleMovimiento(idCaja, null);
  }

  @Override
  public ResultadoSP filtrarPorCaja(Integer idCaja) {
    return movimientoRepository.filtrarPorCaja(idCaja);
  }
}
