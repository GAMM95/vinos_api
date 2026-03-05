package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.domain.view.DistribucionView;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface DistribucionService {
  ResultadoSP distribuirProducto(DistribucionSucursal distribucionSucursal);

  ResponseVO filtrarRepartosPorSucursalORango(Integer idSucursal, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  ResponseVO listarRepartoSucursal(int pagina, int limite);
}
