package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.view.AlmacenView;

import java.util.List;

public interface AlmacenService {

  List<AlmacenView> listarStockDetallado();

  List<AlmacenView> listarStockPorVino();

  List<AlmacenView> listarStockPorOrigen();
}
