package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.CatalogoDTO;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.repository.CatalogoRepository;
import com.gamm.vinos_api.service.CatalogoService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoServiceImpl extends BaseService implements CatalogoService {

  private final CatalogoRepository catalogoRepository;

  @Override
  public ResultadoSP registrarCatalogo(Catalogo catalogo) {
    ResultadoSP resultado =  catalogoRepository.registrarCatalogo(catalogo);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP actualizarCatalogo(Catalogo catalogo) {
    ResultadoSP resultado = catalogoRepository.actualizarCatalogo(catalogo);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP darDeBajaCatalogo(Integer idCatalogo) {
    ResultadoSP resultado = catalogoRepository.darDeBajaCatalogo(idCatalogo);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP darDeAltaCatalogo(Integer idCatalogo) {
    ResultadoSP resultado = catalogoRepository.darDeAltaCatalogo(idCatalogo);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP filtrarPorProveedor(Integer idProveedor) {
    ResultadoSP resultado = catalogoRepository.filtrarPorProveedor(idProveedor);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public List<CatalogoDTO> listarCatalogos() {
    return catalogoRepository.listarCatalogos();
  }

  @Override
  public PaginaResultado<CatalogoDTO> listarCatalogosPaginadosPorProveedor(Integer idProveedor, int pagina, int limite) {
    List<CatalogoDTO> data = catalogoRepository.listarCatalogosPaginados(idProveedor, pagina, limite);
    Long total = catalogoRepository.contarCatalogos(idProveedor);
    return construirPagina(data, pagina, limite,total);
  }

}
