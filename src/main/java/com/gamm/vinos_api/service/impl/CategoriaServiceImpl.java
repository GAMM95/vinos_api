package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.entities.model.Categoria;
import com.gamm.vinos_api.repository.CategoriaRepository;
import com.gamm.vinos_api.service.CategoriaService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

  private final CategoriaRepository categoriaRepository;

  @Override
  public ResultadoSP registrarCategoria(Categoria categoria) {
    return categoriaRepository.registrarCategoria(categoria);
  }

  @Override
  public ResultadoSP actualizarCategoria(Categoria categoria) {
    return categoriaRepository.actualizarCategoria(categoria);
  }

  @Override
  public ResultadoSP cambiarEstado(Integer idCategoria) {
    return categoriaRepository.cambiarEstado(idCategoria);
  }

  @Override
  public List<Categoria> listarCategorias() {
    return categoriaRepository.listarCategorias();
  }

  @Override
  public List<Categoria> comboCategoria() {
    return categoriaRepository.comboCategorias();
  }

}
