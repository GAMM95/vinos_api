package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.cbo.*;

import java.util.List;

public interface CombosService {
  List<UnidadVolumenCbo> comboUnidadVolumen();

  List<CategoriaCbo> comboCategoria();

  List<ProveedorCbo> comboProveedor();

  List<PresentacionCbo> comboPresentacion();

  List<VinoCbo> comboVino();

  List<SucursalCbo> comboSucursal();

  List<PresentacionChk> checkBoxPresentacion();

  List<UsuarioCbo> comboUsuario();
}
