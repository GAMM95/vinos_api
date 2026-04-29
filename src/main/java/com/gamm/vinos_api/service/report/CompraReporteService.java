package com.gamm.vinos_api.service.report;

import com.gamm.vinos_api.dto.view.CompraDTO;

import java.util.List;

public interface CompraReporteService {
  // ─── Datos JSON ───────────────────────────────────────────────────────
  List<CompraDTO> obtenerTodas();
  List<CompraDTO> obtenerDetalle(Integer idCompra);


  byte[] exportarTodasPdf();
  byte[] exportarDetallePdf(Integer idCompra);
}
