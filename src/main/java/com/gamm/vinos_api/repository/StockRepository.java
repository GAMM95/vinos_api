package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.view.StockDTO;

import java.util.List;

public interface StockRepository {
  List<StockDTO> listarStockSucursal();

  List<StockDTO> listarStockSucursalDetalladoPorSucursal(Integer idSucursal);

  List<StockDTO> listarStockPorSucursal(Integer idSucursal);

  List<StockDTO> listarStockVenta (Integer idSucursal);
}
