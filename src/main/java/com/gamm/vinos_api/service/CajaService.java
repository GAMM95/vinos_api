package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.CajaDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface CajaService {


  // ─── Operaciones ──────────────────────────────────────────────────────────
  ResultadoSP abrirCaja(Caja caja);

  ResultadoSP cerrarCaja(Integer idCaja);

  ResultadoSP obtenerSiguienteCodigoCaja();


  // ─── Consultas del usuario autenticado ────────────────────────────────────
  List<CajaDTO> mostrarMiUltimaCajaAbierta();

  PaginaResultado<CajaDTO> listarMisCajas(int pagina, int limite);

  PaginaResultado<CajaDTO> filtrarMisCajasPorRango(
      LocalDate fechaInicio, LocalDate fechaFin,
      int pagina, int limite
  );

  // ─── Administración ───────────────────────────────────────────────────────
  PaginaResultado<CajaDTO> listarTotalCajas(int pagina, int limite);

  PaginaResultado<CajaDTO> filtrarCajasPorUsuarioORango(
      Integer idUsuario,
      LocalDate fechaInicio, LocalDate fechaFin,
      int pagina, int limite
  );

}
