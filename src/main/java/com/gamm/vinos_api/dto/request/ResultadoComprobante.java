package com.gamm.vinos_api.dto.request;

public record ResultadoComprobante(
    byte[] pdfA4,       // se guarda en disco
    byte[] pdfTicket,   // se devuelve al cliente para imprimir
    String numeroComprobante,
    Integer idComprobante
) {}