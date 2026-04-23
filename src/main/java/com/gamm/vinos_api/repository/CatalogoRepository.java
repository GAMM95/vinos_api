package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.view.CatalogoDTO;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface CatalogoRepository {

  ResultadoSP registrarCatalogo(Catalogo catalogo);

  ResultadoSP actualizarCatalogo(Catalogo catalogo);

  ResultadoSP darDeBajaCatalogo(Integer idCatalogo);

  ResultadoSP darDeAltaCatalogo(Integer idCatalogo);

  List<CatalogoDTO> listarCatalogos();

  List<CatalogoDTO> listarCatalogosPaginados(Integer idProveedor, int pagina, int limite);

  Long contarCatalogos(Integer idProveedor);

  ResultadoSP filtrarPorProveedor(Integer idProveedor);

}
