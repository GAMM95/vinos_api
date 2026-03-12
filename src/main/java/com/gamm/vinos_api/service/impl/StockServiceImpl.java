package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.StockView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.StockRepository;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

  @Autowired
  private StockRepository stockRepository;


  /* Helpers */
  private Integer getSucursalAutenticado() {
    Integer idSucursal = SecurityUtils.getSucursalId();
    if (idSucursal == null) {
      throw new IllegalArgumentException("El id del sucursal no es valido");
    }
    return idSucursal;
  }

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
    List<StockView> data = stockRepository.listarStockPorSucursal(getSucursalAutenticado());
    return ResponseVO.success(data);
  }

  @Override
  public ResponseVO listarStockVenta() {
    List<StockView> data = stockRepository.listarStockVenta(getSucursalAutenticado());
    return ResponseVO.success(data);
  }
}
