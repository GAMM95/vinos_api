package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.AlmacenView;
import com.gamm.vinos_api.repository.AlmacenRepository;
import com.gamm.vinos_api.service.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlmacenServiceImpl implements AlmacenService {

  @Autowired
  private AlmacenRepository almacenRepository;

  @Override
  public List<AlmacenView> listarStockDetallado() {
    return almacenRepository.listarStockDetallado();
  }

  @Override
  public List<AlmacenView> listarStockPorVino() {
    return almacenRepository.listarStockPorVino();
  }

  @Override
  public List<AlmacenView> listarStockPorOrigen() {
    return almacenRepository.listarStockPorOrigen();
  }

  @Override
  public List<AlmacenView> listarStockADistribuir() {
    return almacenRepository.listarStockADistribuir();
  }
}
