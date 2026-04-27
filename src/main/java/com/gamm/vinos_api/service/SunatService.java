package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.queries.ComprobanteDTO;

import java.util.List;

public interface SunatService {
  /**
   * Envía el comprobante a SUNAT.
   * Retorna true si fue aceptado, false si fue rechazado o si SUNAT está inactivo.
   */
  boolean enviar(ComprobanteDTO cabecera, List<ComprobanteDTO> detalles);
}
