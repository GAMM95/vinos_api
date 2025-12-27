package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.view.CatalogoView;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface CatalogoService {
  ResultadoSP registrarCatalogo(Catalogo catalogo);

  ResultadoSP actualizarCatalogo(Catalogo catalogo);

  ResultadoSP darDeBajaCatalogo(Integer idCatalogo);

  ResultadoSP darDeAltaCatalogo(Integer idCatalogo);

  List<CatalogoView> listarCatalogos();

  ResultadoSP filtrarPorProveedor(Integer idProveedor);
}
