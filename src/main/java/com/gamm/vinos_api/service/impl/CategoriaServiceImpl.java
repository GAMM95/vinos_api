package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.CategoriaDTO;
import com.gamm.vinos_api.repository.CategoriaRepository;
import com.gamm.vinos_api.service.CategoriaService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

  private final CategoriaRepository categoriaRepository;

  @Override
  public ResultadoSP registrarCategoria(Categoria categoria) {
    ResultadoSP resultado = categoriaRepository.registrarCategoria(categoria);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP actualizarCategoria(Categoria categoria) {
    ResultadoSP resultado =  categoriaRepository.actualizarCategoria(categoria);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP cambiarEstado(Integer idCategoria) {
    ResultadoSP resultado = categoriaRepository.cambiarEstado(idCategoria);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public List<CategoriaDTO> listarCategorias() {
    return categoriaRepository.listarCategorias();
  }

}
