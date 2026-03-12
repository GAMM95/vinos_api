package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface CategoriaRepository {
    ResultadoSP registrarCategoria(Categoria categoria);

    ResultadoSP actualizarCategoria(Categoria categoria);

    ResultadoSP cambiarEstado(Integer idCategoria);

    List<Categoria> listarCategorias();

    List<Categoria> comboCategorias();
}
