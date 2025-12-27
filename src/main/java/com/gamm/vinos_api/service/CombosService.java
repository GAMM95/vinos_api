package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.view.CategoriaCbo;
import com.gamm.vinos_api.domain.view.UnidadVolumenCbo;

import java.util.List;

public interface CombosService {
  List<UnidadVolumenCbo> comboUnidadVolumen();

  List<CategoriaCbo> comboCategoria();
}
