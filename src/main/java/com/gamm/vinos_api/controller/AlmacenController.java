package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/almacen")
public class AlmacenController extends AbstractRestController {
  @Autowired
  private AlmacenService almacenService;

  @GetMapping("/stock-detallado")
  public ResponseEntity<ResponseVO> listarStockDetallado() {
    return ok(almacenService.listarStockDetallado());
  }

  @GetMapping("/stock-vino")
  public ResponseEntity<ResponseVO> listarStockPorVino() {
    return ok(almacenService.listarStockPorVino());
  }

  @GetMapping("/stock-origen")
  public ResponseEntity<ResponseVO> listarStockPorOrigen() {
    return ok(almacenService.listarStockPorOrigen());
  }
}