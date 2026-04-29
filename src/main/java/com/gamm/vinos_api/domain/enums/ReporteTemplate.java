package com.gamm.vinos_api.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReporteTemplate {
  // ─── Compras ──────────────────────────────────────────────────────────
  COMPRAS_GENERAL ("reports/compras/reporte_compras_general.jrxml", "compras-general"),
  COMPRAS_DETALLE("reports/compras/reporte_compras_detalle.jrxml", "detalle-compra"),

  // ─── Stock ────────────────────────────────────────────────────────────
  REPORTE_STOCK("reports/reporte_stock.jrxml", "reporte-stock");

  private final String path;
  private final String nombreArchivo;
}
