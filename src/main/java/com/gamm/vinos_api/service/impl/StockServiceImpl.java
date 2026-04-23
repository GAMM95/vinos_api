package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.StockDTO;
import com.gamm.vinos_api.repository.StockRepository;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl extends BaseService implements StockService {

  private final StockRepository stockRepository;

  @Override
  public List<StockDTO> listarStockSucursal() {
    return stockRepository.listarStockSucursal();
  }

  @Override
  public List<StockDTO> listarStockSucursalDetalladoPorSucursal(Integer idSucursal) {
    return stockRepository.listarStockSucursalDetalladoPorSucursal(idSucursal);
  }

  @Override
  public List<StockDTO> listarStockPorSucursal() {
    return stockRepository.listarStockPorSucursal(getIdSucursalAutenticada());
  }

  @Override
  public List<StockDTO> listarStockVenta() {
    return stockRepository.listarStockVenta(getIdSucursalAutenticada());
  }
}
