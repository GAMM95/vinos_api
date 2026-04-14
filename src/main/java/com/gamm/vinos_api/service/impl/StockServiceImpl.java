package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.StockView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.StockRepository;
import com.gamm.vinos_api.service.BaseService;
import com.gamm.vinos_api.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl extends BaseService implements StockService {

  private final StockRepository stockRepository;

  @Override
  public ResponseVO listarStockSucursal() {
    List<StockView> data = stockRepository.listarStockSucursal();
    return ResponseVO.success(data);
  }

  @Override
  public ResponseVO listarStockSucursalDetalladoPorSucursal(Integer idSucursal) {
    List<StockView> data = stockRepository.listarStockSucursalDetalladoPorSucursal(idSucursal);
    return ResponseVO.success(data);
  }

  @Override
  public ResponseVO listarStockPorSucursal() {
    List<StockView> data = stockRepository.listarStockPorSucursal(getIdSucursalAutenticada());
    return ResponseVO.success(data);
  }

  @Override
  public ResponseVO listarStockVenta() {
    List<StockView> data = stockRepository.listarStockVenta(getIdSucursalAutenticada());
    return ResponseVO.success(data);
  }
}
