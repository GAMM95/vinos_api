package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.view.CategoriaCbo;
import com.gamm.vinos_api.domain.view.UnidadVolumenCbo;
import com.gamm.vinos_api.repository.CombosRepository;
import com.gamm.vinos_api.service.CombosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CombosServiceImpl implements CombosService {
  private final CombosRepository combosRepository;

  @Override
  public List<UnidadVolumenCbo> comboUnidadVolumen() {
    return combosRepository.comboUnidadVolumen();
  }

  @Override
  public List<CategoriaCbo> comboCategoria() {
    return combosRepository.comboCategoria();
  }
}
