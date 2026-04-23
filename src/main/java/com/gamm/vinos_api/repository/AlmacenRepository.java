package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.view.AlmacenDTO;

import java.util.List;

public interface AlmacenRepository {

  List<AlmacenDTO> listarStockDetallado();

  List<AlmacenDTO> listarStockPorVino();

  List<AlmacenDTO> listarStockPorOrigen();

  List<AlmacenDTO> listarStockADistribuir();
}