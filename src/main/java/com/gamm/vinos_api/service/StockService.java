package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.view.StockView;
import com.gamm.vinos_api.dto.ResponseVO;

import java.util.List;

public interface StockService {

  ResponseVO listarStockSucursal();

  ResponseVO listarStockSucursalDetalladoPorSucursal(Integer idSucursal);

  ResponseVO listarStockPorSucursal();
}
