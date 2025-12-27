package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.view.CategoriaCbo;
import com.gamm.vinos_api.domain.view.UnidadVolumenCbo;

import java.util.List;

public interface CombosRepository {

  List<UnidadVolumenCbo> comboUnidadVolumen();

  List<CategoriaCbo> comboCategoria();
}
