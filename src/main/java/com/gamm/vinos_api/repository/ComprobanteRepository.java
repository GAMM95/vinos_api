package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.queries.ComprobanteDTO;

import java.util.List;

public interface ComprobanteRepository {
  List<ComprobanteDTO> obtenerComprobantePorVenta(Integer idVenta);

  List<ComprobanteDTO> obtenerComprobantePorId(Integer idComprobante);

  void actualizarEstadoSunat(Integer idComprobante, String estadoSunat);

  void guardarRutaPdf(Integer idComprobante, String rutaPdf);
}
