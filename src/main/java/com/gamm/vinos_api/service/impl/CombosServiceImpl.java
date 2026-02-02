package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.cbo.*;
import com.gamm.vinos_api.domain.view.VinoView;
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

  @Override
  public List<ProveedorCbo> comboProveedor() {
    return combosRepository.comboProveedor();
  }

  @Override
  public List<PresentacionCbo> comboPresentacion() {
    return combosRepository.comboPresentacion();
  }

  @Override
  public List<VinoCbo> comboVino() {
    return combosRepository.comboVino();
  }

  @Override
  public List<SucursalCbo> comboSucursal() {
    return combosRepository.comboSucursal();
  }

  @Override
  public List<PresentacionChk> checkBoxPresentacion() {
    return combosRepository.checkBoxPresentacion();
  }
}
