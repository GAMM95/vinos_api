package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.AlmacenDTO;
import com.gamm.vinos_api.repository.AlmacenRepository;
import com.gamm.vinos_api.service.AlmacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlmacenServiceImpl implements AlmacenService {

  private final  AlmacenRepository almacenRepository;

  @Override
  public List<AlmacenDTO> listarStockDetallado() {
    return almacenRepository.listarStockDetallado();
  }

  @Override
  public List<AlmacenDTO> listarStockPorVino() {
    return almacenRepository.listarStockPorVino();
  }

  @Override
  public List<AlmacenDTO> listarStockPorOrigen() {
    return almacenRepository.listarStockPorOrigen();
  }

  @Override
  public List<AlmacenDTO> listarStockADistribuir() {
    return almacenRepository.listarStockADistribuir();
  }
}
