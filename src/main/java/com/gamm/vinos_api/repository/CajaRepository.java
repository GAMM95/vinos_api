package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.dto.view.CajaDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface CajaRepository {
  // Tipo 1: Abrir caja
  ResultadoSP abrirCaja(Caja caja);

  CajaDTO obtenerUltimaCajaAbiertaUsuario(Integer idUsuario);

  // Tipo 2: Cerrar caja
  ResultadoSP cerrarCaja(Integer idCaja);

  // Tipo 3: Filtrar mis cajas por rango de fecha
  ResultadoSP filtrarMisCajasPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  // Tipo 4: Filtrar las cajas por usuario y/o rango de fechas
  ResultadoSP filtrarTotalCajasPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  // Contar mis cajas por rango de fechas
  long contarMisCajasPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  // Contar todas las cajas por usuario y/o rango
  long contarTotalCajasPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  // Listar mis cajas
  long contarMisCajas(Integer idUsuario);

  List<CajaDTO> listarMisCajas(Integer idUsuario, int pagina, int limite);

  // Listar todas las cajas

  long contarTotalCajas();

  List<CajaDTO> listarTotalCajas(int pagina, int limite);

  List<CajaDTO> mostrarMiUltimaCajaAbierta (Integer idUsuario);

  ResultadoSP obtenerSiguienteCodigoCaja(Integer idSucursal);

  CajaDTO obtenerCajaPorId(Integer idCaja);
}
