package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.MovimientosDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoService {

  PaginaResultado<MovimientosDTO> filtrarMisMovimientosPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  PaginaResultado<MovimientosDTO>  filtrarMovimientosPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  PaginaResultado<MovimientosDTO>  listarMisMovimientos(int pagina, int limite);

  PaginaResultado<MovimientosDTO>  listarTotalMovimientos(int pagina, int limite);

  List<MovimientosDTO> listarDetalleMovimientoUsuario(Integer idCaja);

  List<MovimientosDTO>  listarDetalleMovimientoAdmin(Integer idCaja);

  ResultadoSP filtrarPorCaja(Integer idCaja);

}
