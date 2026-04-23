package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.view.StockDTO;

import java.util.List;

public interface StockService {

  List<StockDTO> listarStockSucursal();

  List<StockDTO> listarStockSucursalDetalladoPorSucursal(Integer idSucursal);

  List<StockDTO> listarStockPorSucursal();

  List<StockDTO> listarStockVenta();
}
