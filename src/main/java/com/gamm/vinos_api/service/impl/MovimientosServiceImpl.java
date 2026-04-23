package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.MovimientosDTO;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.MovimientoRepository;
import com.gamm.vinos_api.service.base.BaseService;
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

  @Override
  public PaginaResultado<MovimientosDTO> filtrarMisMovimientosPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    ResultadoSP resultadoSP = movimientoRepository.filtrarMisMovimientosPorRango(idUsuario, fechaInicio, fechaFin, pagina, limite);
    ResponseVO.validar(resultadoSP);

    @SuppressWarnings("unchecked")
    List<MovimientosDTO> data = resultadoSP.getData() != null
        ? (List<MovimientosDTO>) resultadoSP.getData()
        : List.of();

    long totalRegistros = movimientoRepository.contarMisMovimientosPorRango(idUsuario, fechaInicio, fechaFin);

    return construirPagina(data, pagina, limite, totalRegistros);
  }

  @Override
  public PaginaResultado<MovimientosDTO> filtrarMovimientosPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {

    ResultadoSP resultadoSP = movimientoRepository.filtrarMovimientosPorUsuarioORango(idUsuario, fechaInicio, fechaFin, pagina, limite);
    ResponseVO.validar(resultadoSP);

    @SuppressWarnings("unchecked")
    List<MovimientosDTO> data = resultadoSP.getData() != null
        ? (List<MovimientosDTO>) resultadoSP.getData()
        : List.of();

    long total = movimientoRepository.contarMovimientosPorRango(idUsuario, fechaInicio, fechaFin);

    return construirPagina(data, pagina, limite, total);
  }

  @Override
  public PaginaResultado<MovimientosDTO> listarMisMovimientos(int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();

    List<MovimientosDTO> data = movimientoRepository.listarMisMovimientos(idUsuario, pagina, limite);

    long total = movimientoRepository.contarMisMovimientos(idUsuario);
    return construirPagina(data, pagina, limite, total);
  }

  @Override
  public PaginaResultado<MovimientosDTO> listarTotalMovimientos(int pagina, int limite) {
    List<MovimientosDTO> data = movimientoRepository.listarTotalMovimientos(pagina, limite);
    long total = movimientoRepository.contarTotalMovimientos();
    return construirPagina(data, pagina, limite, total);
  }

  // ─── Detalles ────────────────────────────────────────────

  /* Helpers */
  private List<MovimientosDTO> listarDetalleMovimiento(Integer idCaja, Integer idUsuario) {
    return idUsuario != null
        ? movimientoRepository.listarDetalleMovimientoUsuario(idUsuario, idCaja)
        : movimientoRepository.listarDetalleMovimiento(idCaja);
  }

  @Override
  public List<MovimientosDTO> listarDetalleMovimientoUsuario(Integer idCaja) {
    return listarDetalleMovimiento(idCaja, getIdUsuarioAutenticado());
  }

  @Override
  public List<MovimientosDTO> listarDetalleMovimientoAdmin(Integer idCaja) {
    return listarDetalleMovimiento(idCaja, null);
  }

  @Override
  public ResultadoSP filtrarPorCaja(Integer idCaja) {
    return movimientoRepository.filtrarPorCaja(idCaja);
  }
}
