package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.DistribucionDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.time.LocalDate;

public interface DistribucionService {
  ResultadoSP distribuirProducto(DistribucionSucursal distribucionSucursal);

  PaginaResultado<DistribucionDTO> filtrarRepartosPorSucursalORango(Integer idSucursal, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  PaginaResultado<DistribucionDTO> listarRepartoSucursal(int pagina, int limite);
}
