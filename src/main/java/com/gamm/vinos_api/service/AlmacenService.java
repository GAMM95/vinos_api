package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.view.AlmacenDTO;

import java.util.List;

public interface AlmacenService {

  List<AlmacenDTO> listarStockDetallado();

  List<AlmacenDTO> listarStockPorVino();

  List<AlmacenDTO> listarStockPorOrigen();

  List<AlmacenDTO> listarStockADistribuir();
}
