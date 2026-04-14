package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Stock", description = "Consulta de stock por sucursal")
@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController extends AbstractRestController {

  private final StockService stockService;

  @Operation(summary = "Listar stock de todas las sucursales")
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarStock() {
    return ok(stockService.listarStockSucursal());
  }

  @Operation(summary = "Listar stock detallado de una sucursal")
  @GetMapping("/sucursales/{idSucursal}") // ✅ jerarquía correcta
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarStockDetalladoPorSucursal(@PathVariable Integer idSucursal) {
    return ok(stockService.listarStockSucursalDetalladoPorSucursal(idSucursal));
  }

  @Operation(summary = "Listar stock de la sucursal del usuario autenticado")
  @GetMapping("/sucursal/actual") // ✅ /mi-sucursal → /sucursal/actual
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarStockPorSucursal() {
    return ok(stockService.listarStockPorSucursal());
  }

  @Operation(summary = "Listar stock disponible para venta")
  @GetMapping("/venta") // ✅ /stock-venta → /venta
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarStockVenta() {
    return ok(stockService.listarStockVenta());
  }
}
