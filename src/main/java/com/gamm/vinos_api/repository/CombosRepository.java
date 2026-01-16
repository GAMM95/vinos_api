package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.cbo.*;
import com.gamm.vinos_api.domain.view.VinoView;

import java.util.List;

public interface CombosRepository {

  List<UnidadVolumenCbo> comboUnidadVolumen();

  List<CategoriaCbo> comboCategoria();

  List<ProveedorCbo> comboProveedor();

  List<PresentacionCbo> comboPresentacion();

  List<VinoCbo> comboVino();

  List<SucursalCbo> comboSucursal();
}
