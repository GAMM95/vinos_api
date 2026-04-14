package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.view.AlmacenView;

import java.util.List;

public interface AlmacenRepository {

  List<AlmacenView> listarStockDetallado();

  List<AlmacenView> listarStockPorVino();

  List<AlmacenView> listarStockPorOrigen();

  List<AlmacenView> listarStockADistribuir();
}