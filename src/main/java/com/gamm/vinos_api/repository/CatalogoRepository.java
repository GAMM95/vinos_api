package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.view.CatalogoView;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface CatalogoRepository {

  ResultadoSP registrarCatalogo(Catalogo catalogo);

  ResultadoSP actualizarCatalogo(Catalogo catalogo);

  ResultadoSP darDeBajaCatalogo(Integer idCatalogo);

  ResultadoSP darDeAltaCatalogo(Integer idCatalogo);

  List<CatalogoView> listarCatalogos();

  List<CatalogoView> listarCatalogosPaginados(Integer idProveedor, int pagina, int limite);

  Long contarCatalogos(Integer idProveedor);

  ResultadoSP filtrarPorProveedor(Integer idProveedor);


//  ResultadoSP filtrarPorIDProveedorTermino (Integer idProveedor, String termino);
}
