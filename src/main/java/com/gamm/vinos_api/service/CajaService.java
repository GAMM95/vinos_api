package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.domain.view.CajaView;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface CajaService {
  ResultadoSP abrirCaja(Caja caja);

  ResultadoSP cerrarCaja(Integer idCaja);

  ResponseVO listarMisCajas(int pagina, int limite);

  ResponseVO listarTotalCajas(int pagina, int limite);

  ResponseVO filtrarMisCajasPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  ResponseVO filtrarCajasPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  List<CajaView> mostrarMiUltimaCajaAbierta();

  ResultadoSP obtenerSiguienteCodigoCaja();
}
