package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.VentaService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Tag(name = "Ventas", description = "Operaciones relacionadas a ventas, detalle de ventas y carrito de ventas")
@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController extends AbstractRestController {

  private final VentaService ventaService;

  // Listar productos del carrito del usuario logueado
  @Operation(summary = "Listar productos del carrito de venta del usuario logueado.")
  @GetMapping("/carrito-user")
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarCarritoVentaUser() {
    return ok(ventaService.listarCarritoVentaUsuario());
  }

  // Listar productos del carrito de cualquier usuario
  @Operation(summary = "Listar carritos de venta de cualquier usuario.")
  @GetMapping("/carrito")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarCarritoVenta(
      @RequestParam Integer idVenta
  ) {
    return ok(ventaService.listarCarritoVentaAdmin(idVenta));
  }

  // Agregar carrito de ventas
  @Operation(summary = "Agregar producto al carrito de ventas.")
  @PostMapping("/carrito")
  @SoloVendedor
  public ResponseEntity<ResponseVO> agregarProductoCarrito(
      @RequestBody Venta venta
  ) {
    ResultadoSP resultado = ventaService.agregarCarritoVenta(venta);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Confirmar venta
  @Operation(summary = "Confirmar una venta.")
  @PostMapping("/confirmar")
  @SoloVendedor
  public ResponseEntity<ResponseVO> confirmarVenta(
      @RequestParam Integer idVenta,
      @RequestParam String metodoPago,
      @RequestParam BigDecimal descuento
  ) {

    ResultadoSP resultado = ventaService.confirmarVenta(idVenta, metodoPago, descuento);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Retirar producto del carrito
  @Operation(summary = "Retirar producto del carrito")
  @DeleteMapping("/carrito")
  @SoloVendedor
  public ResponseEntity<ResponseVO> retirarProductoCarrito(
      @RequestParam Integer idVenta,
      @RequestParam Integer idVino
  ) {

    ResultadoSP resultado = ventaService.retirarProductoCarrito(idVenta, idVino);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }
}