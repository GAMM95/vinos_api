package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Compras", description = "Gestión de compras, carrito y estados")
@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
public class CompraController extends AbstractRestController {

  private final CompraService compraService;

  // ─── Carrito ──────────────────────────────────────────────────────────────

  @Operation(summary = "Agregar producto al carrito")
  @PostMapping("/carrito")
  public ResponseEntity<ResponseVO> agregarProductoCarrito(@Valid @RequestBody Compra compra) {
    ResultadoSP resultado = compraService.agregarProductoCarrito(compra);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Actualizar cantidad de producto en el carrito")
  @PutMapping("/carrito")
  public ResponseEntity<ResponseVO> actualizarCantidadProductoCarrito(@RequestBody Compra compra) {
    ResultadoSP resultado = compraService.actualizarCantidadProductoCarrito(compra);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Eliminar producto del carrito")
  @DeleteMapping("/carrito/{idDetalleCompra}")
  public ResponseEntity<ResponseVO> eliminarProductoCarrito(@PathVariable Integer idDetalleCompra) {
    ResultadoSP resultado = compraService.eliminarProductoCarrito(idDetalleCompra);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Contar productos del carrito del usuario autenticado")
  @GetMapping("/carrito/cantidad")
  public ResponseEntity<ResponseVO> contarProductosCarrito() {
    long cantidad = compraService.contarProductosCarritoUsuario();
    return ok("Cantidad de productos en el carrito", cantidad);
  }

  @Operation(summary = "Listar productos del carrito del usuario autenticado")
  @GetMapping("/carrito")
  public ResponseEntity<ResponseVO> listarProductosCarrito() {
    return ok(compraService.listarProductosCarritoUsuario());
  }

  @Operation(summary = "Listar total de carritos de todos los usuarios")
  @GetMapping("/carrito/total")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalCarritosCompra(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "3") int limite) {
    return okPaginado(compraService.listarTotalCompras(pagina, limite));
  }

  // ─── Compras del usuario autenticado ─────────────────────────────────────

  @Operation(summary = "Listar compras del usuario autenticado")
  @GetMapping("/compras-user") //mias
  public ResponseEntity<ResponseVO> listarComprasUsuario(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    return okPaginado(compraService.listarComprasUsuario(pagina, limite));
  }

  @Operation(summary = "Filtrar mis compras por rango de fechas")
  @GetMapping("/user/filtrar") // mias/filtro
  public ResponseEntity<ResponseVO> filtrarMisComprasPorFechas(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    return okPaginado(compraService.filtrarMisComprasPorFechas(fechaInicio, fechaFin, pagina, limite));
  }

  @Operation(summary = "Ver detalle de una compra del usuario autenticado")
  @GetMapping("/{idCompra}/detalle")  // ✅ /detalle-user → /detalle
  public ResponseEntity<ResponseVO> detalleCompraUsuario(@PathVariable Integer idCompra) {
    return ok(compraService.listarDetalleCompraUsuario(idCompra));
  }

  // ─── Compras pendientes ───────────────────────────────────────────────────

  @Operation(summary = "Listar compras pendientes")
  @GetMapping("/pendientes")
  public ResponseEntity<ResponseVO> listarComprasPendientes() {
    return ok(compraService.listarComprasPendientes());
  }

  // ─── Administración ───────────────────────────────────────────────────────

  @Operation(summary = "Confirmar compra")
  @PostMapping
  public ResponseEntity<ResponseVO> confirmarCompra(
      @RequestBody(required = false) Compra compra
  ) {
    ResultadoSP resultado = compraService.confirmarCompra(compra);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Cambiar estado de la compra (cerrar o deshacer cierre)")
  @PatchMapping("/{idCompra}/estado")
  public ResponseEntity<ResponseVO> cambiarEstadoCompra(
      @PathVariable Integer idCompra,
      @RequestParam boolean cerrada
  ) {
    ResultadoSP resultado = cerrada
        ? compraService.cerrarCompra(idCompra)
        : compraService.deshacerCerrarCompra(idCompra);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Anular compra")
  @PatchMapping("/{idCompra}/anulacion") // ✅ PUT → PATCH + sustantivo
  public ResponseEntity<ResponseVO> anularCompra(@PathVariable Integer idCompra) {
    ResultadoSP resultado = compraService.anularCompra(idCompra);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Revertir compra")
  @PatchMapping("/{idCompra}/reversion") // ✅ PUT → PATCH + sustantivo
  public ResponseEntity<ResponseVO> revertirCompra(@PathVariable Integer idCompra) {
    ResultadoSP resultado = compraService.revertirCompra(idCompra);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Filtrar compras por usuario y/o rango de fechas")
  @GetMapping("/filtro") // ✅ /filtrar → /filtro
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarComprasPorUsuarioYFechas(
      @RequestParam(required = false) Integer idUsuario,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    return okPaginado(compraService.filtrarComprasPorUsuarioYFechas(idUsuario, fechaInicio, fechaFin, pagina, limite));
  }

  @Operation(summary = "Ver detalle de una compra (vista administrador)")
  @GetMapping("/{idCompra}/detalle/admin")
  public ResponseEntity<ResponseVO> detalleCompraAdmin(@PathVariable Integer idCompra) {
    return ok(compraService.listarDetalleCompraAdmin(idCompra));
  }

  @Operation(summary = "Listar compras confirmadas")
  @GetMapping("/confirmadas")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarComprasConfirmadas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    return okPaginado(compraService.listarComprasConfirmadas(pagina, limite));
  }

  @Operation(summary = "Listar compras anuladas")
  @GetMapping("/anuladas")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarComprasAnuladas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    return okPaginado(compraService.listarComprasAnuladas(pagina, limite));
  }

}
