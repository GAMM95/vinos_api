package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.CatalogoDTO;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface CatalogoService {
  // Mutaciones
  ResultadoSP registrarCatalogo(Catalogo catalogo);

  ResultadoSP actualizarCatalogo(Catalogo catalogo);

  ResultadoSP darDeBajaCatalogo(Integer idCatalogo);

  ResultadoSP darDeAltaCatalogo(Integer idCatalogo);

  ResultadoSP filtrarPorProveedor(Integer idProveedor);

  ResultadoSP filtrarPorTermino(Integer idProveedor, String termino);
  // Consultas
  List<CatalogoDTO> listarCatalogos();

  PaginaResultado<CatalogoDTO> listarCatalogosPaginadosPorProveedor(Integer idProveedor, int pagina, int limite);

}
