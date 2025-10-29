package com.gamm.vinos_api.service;


import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface CategoriaService {

  ResultadoSP registrarCategoria(Categoria categoria);

  ResultadoSP actualizarCategoria(Categoria categoria);

  ResultadoSP cambiarEstado(Integer idCategoria);

  // Opcional: agregar métodos de consulta
  List<Categoria> listarCategorias();

  // Listar combo
  List<Categoria> comboCategoria();
}