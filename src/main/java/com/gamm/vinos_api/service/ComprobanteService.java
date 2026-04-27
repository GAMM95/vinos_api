package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.request.ResultadoComprobante;

public interface ComprobanteService {
  // ─── PDF comprobante A4 (se guarda en disco) ──────────────────────────
  byte[] generarPdfPorVenta(Integer idVenta);

  byte[] generarPdfPorComprobante(Integer idComprobante);

  // ─── Ticket 80mm (se imprime + se guarda como respaldo) ───────────────
  byte[] generarTicketPorVenta(Integer idVenta);

  byte[] generarTicketPorComprobante(Integer idComprobante);

  // ─── Genera ambos a la vez al confirmar venta ─────────────────────────
  ResultadoComprobante generarAmbos(Integer idVenta);

  // Preparado para SUNAT — se activa cuando llegue el momento
  void enviarASunat(Integer idComprobante);


}

