package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.view.CompraDTO;

import java.util.List;

public interface ReporteRepository {
  // Compras
  List<CompraDTO> reporteDetalleCompra(Integer idCompra);
}
