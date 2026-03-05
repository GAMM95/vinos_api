package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController extends AbstractRestController {

  private final StockService stockService;

  // Listar stock sucursal
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarStock() {
    return ResponseEntity.ok(stockService.listarStockSucursal());
  }

  // Listar stock detallado
  @GetMapping("/detallado")
  public ResponseEntity<ResponseVO> listarStockDetallado() {
    return ResponseEntity.ok(stockService.listarStockSucursalDetallado());
  }

  // Listar stock para cada sucursal
  @GetMapping("/mi-sucursal")
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarStockPorSucursal() {
    return ResponseEntity.ok(stockService.listarStockPorSucursal());
  }

}
