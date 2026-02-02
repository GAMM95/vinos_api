package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController extends AbstractRestController {

  @Autowired
  private CompraService compraService;

  // Agregar producto al carrito
  @PostMapping("/carrito")
  public ResponseEntity<ResponseVO> agregarProductoCarrito(@RequestBody Compra compra) {
    ResultadoSP resultado = compraService.agregarProductoCarrito(null, compra);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Actualizar cantidad de producto en el carrito
  @PutMapping("/carrito")
  public ResponseEntity<ResponseVO> actualizarCantidadProductoCarrito(@RequestBody Compra compra) {
    ResultadoSP resultado = compraService.actualizarCantidadProductoCarrito(null, compra);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Eliminar producto del carrito
  @DeleteMapping("/carrito/{idDetalleCompra}")
  public ResponseEntity<ResponseVO> eliminarProductoCarrito(@PathVariable Integer idDetalleCompra) {
    ResultadoSP resultado = compraService.eliminarProductoCarrito(null, idDetalleCompra);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Contar productos del carrito
  @GetMapping("/carrito/count")
  public ResponseEntity<ResponseVO> contarProductosCarrito() {
    long cantidad = compraService.contarProductosCarrito(null, null);
    return ok("Cantidad de productos en el carrito: ", cantidad);
  }

  // Listar productos del carrito del usuario logueado
  @GetMapping("/carrito")
  public ResponseEntity<ResponseVO> listarProductosCarrito() {
    return ok(compraService.listarProductosCarrito(null, null));
  }
}
