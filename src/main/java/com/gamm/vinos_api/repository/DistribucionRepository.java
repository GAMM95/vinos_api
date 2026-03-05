package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.domain.view.CajaView;
import com.gamm.vinos_api.domain.view.DistribucionView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface DistribucionRepository {
  // Tipo 1: Distribuir Reparto
  ResultadoSP distribuirProducto (DistribucionSucursal distribucionSucursal);

  // Tipo 2: Filtrar repartos por sucursal y/o rango de fechas
  ResultadoSP filtrarRepartoSucursalRango (Integer idSucursal, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  // Contar repartos por sucursal y/o rango
  long contarRepartosSucursalRango(Integer idSucursal, LocalDate fechaInicio, LocalDate fechaFin);

  // Listar todos los repartos
  long contarRepartos();

  List<DistribucionView> listarRepartosSucursal(int pagina, int limite);
}
