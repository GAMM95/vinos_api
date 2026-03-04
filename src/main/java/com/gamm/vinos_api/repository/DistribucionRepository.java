package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.domain.view.CajaView;
import com.gamm.vinos_api.domain.view.DistribucionView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface DistribucionRepository {
  // Distribuir Reparto
  ResultadoSP distribuirProducto (DistribucionSucursal distribucionSucursal);

  // Listar todos los repartos
  long contarRepartos();

  List<DistribucionView> listarRepartosSucursal(int pagina, int limite);
}
