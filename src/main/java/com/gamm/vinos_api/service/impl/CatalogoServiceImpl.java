package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.view.CatalogoView;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.repository.CatalogoRepository;
import com.gamm.vinos_api.service.CatalogoService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {
  private final CatalogoRepository catalogoRepository;

  @Override
  public ResultadoSP registrarCatalogo(Catalogo catalogo) {
    return catalogoRepository.registrarCatalogo(catalogo);
  }

  @Override
  public ResultadoSP actualizarCatalogo(Catalogo catalogo) {
    return catalogoRepository.actualizarCatalogo(catalogo);
  }

  @Override
  public ResultadoSP darDeBajaCatalogo(Integer idCatalogo) {
    return catalogoRepository.darDeBajaCatalogo(idCatalogo);
  }

  @Override
  public ResultadoSP darDeAltaCatalogo(Integer idCatalogo) {
    return catalogoRepository.darDeAltaCatalogo(idCatalogo);
  }

  @Override
  public List<CatalogoView> listarCatalogos() {
    return catalogoRepository.listarCatalogos();
  }

  @Override
  public ResultadoSP filtrarPorProveedor(Integer idProveedor) {
    return catalogoRepository.filtrarPorProveedor(idProveedor);
  }
}
