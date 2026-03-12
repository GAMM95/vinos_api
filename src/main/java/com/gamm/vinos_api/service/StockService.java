package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.response.ResponseVO;

public interface StockService {

  ResponseVO listarStockSucursal();

  ResponseVO listarStockSucursalDetalladoPorSucursal(Integer idSucursal);

  ResponseVO listarStockPorSucursal();

  ResponseVO listarStockVenta();
}
