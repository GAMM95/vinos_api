package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.view.StockView;

import java.util.List;

public interface StockRepository {
  List<StockView> listarStockSucursal();

  List<StockView> listarStockSucursalDetalladoPorSucursal(Integer idSucursal);

  List<StockView> listarStockPorSucursal(Integer idSucursal);
}
