package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.time.LocalDate;

public interface MovimientoService {

  ResponseVO filtrarMisMovimientosPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  ResponseVO filtrarMovimientosPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  ResponseVO listarMisMovimientos(int pagina, int limite);

  ResponseVO listarTotalMovimientos(int pagina, int limite);

  ResponseVO listarDetalleMovimientoUsuario(Integer idCaja);

  ResponseVO listarDetalleMovimientoAdmin(Integer idCaja);

  ResultadoSP filtrarPorCaja(Integer idCaja);

}
