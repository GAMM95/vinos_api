package com.gamm.vinos_api.repository.report;

import com.gamm.vinos_api.domain.enums.EstadoCompra;
import com.gamm.vinos_api.dto.view.CompraDTO;

import java.util.List;

public interface ReporteCompraRepository {
  List<CompraDTO> listarTodas();
  List<CompraDTO> listarDetalle(Integer idCompra);
}
