package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController extends AbstractRestController {

  @Autowired
  private CompraService compraService;

  // Agregar producto al carrito
  @PostMapping("/carrito")
  public ResponseEntity<ResponseVO> agregarProductoCarrito(@RequestBody Compra compra) {
    ResultadoSP resultado = compraService.agregarProductoCarrito(compra);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Actualizar cantidad de producto en el carrito
  @PutMapping("/carrito")
  public ResponseEntity<ResponseVO> actualizarCantidadProductoCarrito(@RequestBody Compra compra) {
    ResultadoSP resultado = compraService.actualizarCantidadProductoCarrito(compra);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Eliminar producto del carrito
  @DeleteMapping("/carrito/{idDetalleCompra}")
  public ResponseEntity<ResponseVO> eliminarProductoCarrito(@PathVariable Integer idDetalleCompra) {
    ResultadoSP resultado = compraService.eliminarProductoCarrito(idDetalleCompra);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Cerrar y deshacer compra
  @PatchMapping("/{idCompra}/estado")
  public ResponseEntity<ResponseVO> cambiarEstadoCompra(
      @PathVariable Integer idCompra,
      @RequestParam("cerrada") boolean cerrada
  ) {
    ResultadoSP resultado;
    if (cerrada) {
      resultado = compraService.cerrarCompra(idCompra);
    } else {
      resultado = compraService.deshacerCerrarCompra(idCompra);
    }
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Contar productos del carrito
  @GetMapping("/carrito/count")
  public ResponseEntity<ResponseVO> contarProductosCarrito() {
    long cantidad = compraService.contarProductosCarritoUsuario();
    return ok("Cantidad de productos en el carrito", cantidad);
  }

  // Listar productos del carrito del usuario logueado
  @GetMapping("/carrito")
  public ResponseEntity<ResponseVO> listarProductosCarrito() {
    return ok(compraService.listarProductosCarritoUsuario());
  }

  // Confirmar compra
  @PostMapping
  public ResponseEntity<ResponseVO> confirmarCompra(
      @RequestBody(required = false) Compra compra) {
    ResultadoSP resultado = compraService.confirmarCompra(compra);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Listar las compras de cada usuario
  @GetMapping("/compras-user")
  public ResponseEntity<ResponseVO> listarComprasUsuario(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    ResponseVO response = compraService.listarComprasUsuario(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Anular compra
  @PutMapping("/{idCompra}/anular")
  public ResponseEntity<ResponseVO> anularCompra(@PathVariable Integer idCompra) {
    ResultadoSP resultado = compraService.anularCompra(idCompra);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Revertir compra
  @PutMapping("/{idCompra}/revertir")
  public ResponseEntity<ResponseVO> revertirCompra(@PathVariable Integer idCompra) {
    ResultadoSP resultado = compraService.revertirCompra(idCompra);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Filtrar mis compras por rango de fechas
  @GetMapping("/user/filtrar")
  public ResponseEntity<ResponseVO> filtrarMisComprasPorFechas(
      @RequestParam LocalDate fechaInicio,
      @RequestParam LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    ResponseVO response = compraService.filtrarMisComprasPorFechas(fechaInicio, fechaFin, pagina, limite);
    return ResponseEntity.ok(response);
  }

  /**
   * TODO: mejorar con paginado
   */
  @GetMapping("/filtrar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarComprasPorUsuarioYFechas(
      @RequestParam(required = false) Integer idUsuario,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate fechaInicio,

      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    ResponseVO response =
        compraService.filtrarComprasPorUsuarioYFechas(idUsuario, fechaInicio, fechaFin, pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Listar detalle de compras de cada usuario
  @GetMapping("/{idCompra}/detalle-user")
  public ResponseEntity<ResponseVO> detalleCompraUsuario(
      @PathVariable Integer idCompra
  ) {
    ResponseVO response = compraService.listarDetalleCompraUsuario(idCompra);
    return ResponseEntity.ok(response);
  }

  // Listar detalle de compras de todos los usuarios para el admin
  @GetMapping("/{idCompra}/detalle-admin")
  public ResponseEntity<ResponseVO> detalleCompraAdmin(
      @PathVariable Integer idCompra
    ) {
    return ResponseEntity.ok(
        compraService.listarDetalleCompraAdmin(idCompra)
    );
  }

  // Listar todas las compras en carrito de todos los usuarios
  @GetMapping("/total-carrito")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalCarritosCompra(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "3") int limite) {
    ResponseVO response = compraService.listarTotalCompras(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Listar compras confirmadas
  @GetMapping("/confirmadas")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarComprasConfirmadas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    ResponseVO response = compraService.listarComprasConfirmadas(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Listar compras pendientes
  @GetMapping("/pendientes")
  public ResponseEntity<ResponseVO> listarComprasPendientes() {
    return ok(compraService.listarComprasPendientes());
  }

  // Listar compras anuladas
  @GetMapping("/anuladas")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarComprasAnuladas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    ResponseVO response = compraService.listarComprasAnuladas(pagina, limite);
    return ResponseEntity.ok(response);
  }
}
