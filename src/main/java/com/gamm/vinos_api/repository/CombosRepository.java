package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.cbo.*;

import java.util.List;

public interface CombosRepository {

  List<UnidadVolumenComboDTO> comboUnidadVolumen();

  List<CategoriaComboDTO> comboCategoria();

  List<ProveedorComboDTO> comboProveedor();

  List<PresentacionComboDTO> comboPresentacion();

  List<VinoCbo> comboVino();

  List<SucursalCbo> comboSucursal();

  List<UsuarioCbo> comboUsuario();
}
