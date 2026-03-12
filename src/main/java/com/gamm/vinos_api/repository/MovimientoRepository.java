package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.view.MovimientosView;
import com.gamm.vinos_api.util.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository {
  // Tipo 1: Filtrar mis movimientos por rango de fecha
  ResultadoSP filtrarMisMovimientosPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  // Contar mis movimientos por rango de fechas
  long contarMisMovimientosPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  // Tipo 2: Filtrar movimientos por usuario y/o rango de fechas
  ResultadoSP filtrarMovimientosPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  // Contar movimientos por usuario y/o rango de fechas
  long contarMovimientosPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  // Listar mis movimientos
  long contarMisMovimientos(Integer idUsuario);

  List<MovimientosView> listarMisMovimientos(Integer idUsuario, int pagina, int limite);

  // Listar todos los movimientos
  long contarTotalMovimientos();

  List<MovimientosView> listarTotalMovimientos(int pagina, int limite);

  List<MovimientosView> listarDetalleMovimientoUsuario(Integer idUsuario, Integer idCaja);

  List<MovimientosView> listarDetalleMovimiento(Integer idCaja);

  ResultadoSP filtrarPorCaja(Integer idCaja);
}
