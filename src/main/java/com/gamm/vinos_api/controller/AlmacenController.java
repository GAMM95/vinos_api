package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.AlmacenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "Almacén",
    description = "Consultas relacionadas al stock general detalle por vino y su origen o procedencia "
)
@RestController
@RequestMapping("/api/v1/almacenes")
@RequiredArgsConstructor
public class AlmacenController extends AbstractRestController {

  private final AlmacenService almacenService;

  @Operation(summary = "Lista el stock detallado que hay en el almacén antes de la distribución a cada sucursal")
  @GetMapping("/stock-detallado")
  public ResponseEntity<ResponseVO> listarStockDetallado() {
    return ok(almacenService.listarStockDetallado());
  }

  @Operation(summary = "Lista el stock que hay en el almacén antes de la distribución por nombre de vino")
  @GetMapping("/stock-vino")
  public ResponseEntity<ResponseVO> listarStockPorVino() {
    return ok(almacenService.listarStockPorVino());
  }

  @Operation(summary = "Lista el stock que hay en el almacén antes de la distribución por origen o procedencia del vino")
  @GetMapping("/stock-origen")
  public ResponseEntity<ResponseVO> listarStockPorOrigen() {
    return ok(almacenService.listarStockPorOrigen());
  }

  @Operation(summary = "Lista el stock que hay en el almacén para su distribución")
  @GetMapping("/stock-distribuir")
  public ResponseEntity<ResponseVO> listarStockADistribuir() {
    return ok(almacenService.listarStockADistribuir());
  }

}