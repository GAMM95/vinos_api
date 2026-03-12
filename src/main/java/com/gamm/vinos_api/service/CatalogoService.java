package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.view.CatalogoView;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface CatalogoService {
  ResultadoSP registrarCatalogo(Catalogo catalogo);

  ResultadoSP actualizarCatalogo(Catalogo catalogo);

  ResultadoSP darDeBajaCatalogo(Integer idCatalogo);

  ResultadoSP darDeAltaCatalogo(Integer idCatalogo);

  List<CatalogoView> listarCatalogos();

  ResponseVO listarCatalogosPaginadosPorProveedor(Integer idProveedor, int pagina, int limite);

  ResultadoSP filtrarPorProveedor(Integer idProveedor);

//  ResultadoSP filtrarPorProveedorYTermino(Integer idProveedor, String termino);
}
