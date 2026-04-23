package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.dto.view.CategoriaDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface CategoriaService {

  ResultadoSP registrarCategoria(Categoria categoria);

  ResultadoSP actualizarCategoria(Categoria categoria);

  ResultadoSP cambiarEstado(Integer idCategoria);

  List<CategoriaDTO> listarCategorias();
}