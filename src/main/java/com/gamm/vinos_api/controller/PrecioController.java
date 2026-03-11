package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.domain.view.PrecioView;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.PrecioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/precios")
@RequiredArgsConstructor
public class PrecioController extends AbstractRestController {

  private final PrecioService precioService;

  // Asignar precio
  @PostMapping
  public ResponseEntity<ResponseVO> asignarPrecio(
      @RequestBody PrecioSucursal precio
  ) {
    ResultadoSP resultado = precioService.asignarPrecio(precio);
    return resultado.esExitoso()
        ? created(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Listar todos los precios del stock (admin)
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalPreciosStock() {
    List<PrecioView> lista = precioService.listarTotalPreciosStock();
    return ok(lista);
  }

  // Listar precios de la sucursal autenticada (vendedor)
  @GetMapping("/mi-sucursal")
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarPreciosSucursal() {
    List<PrecioView> lista = precioService.listarPreciosStockSucursal();
    return ok(lista);
  }

  // Filtrar por vino  o sucursal (admin)
  @GetMapping("/filtrar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarPorVinoOSucursal(
      @RequestParam(required = false) String nombreVino,
      @RequestParam(required = false) Integer idSucursal
  ) {
    ResultadoSP resultado = precioService.filtrarPorVinoOSucursal(nombreVino, idSucursal);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Ver historial de precios de un  vino
  @GetMapping("/detalle")
  public ResponseEntity<ResponseVO> listarDetallePrecios(
      @RequestParam Integer idVino,
      @RequestParam Integer idSucursal
  ) {
    List<PrecioView> lista = precioService.listarPreciosDetalle(idVino, idSucursal);
    return ok(lista);
  }

  // Filtrar historial por nombre de vino
  @GetMapping("/filtrar-vino")
  public ResponseEntity<ResponseVO> filtrarPorVino(
      @RequestParam String nombreVino
  ) {
    ResultadoSP resultado = precioService.filtrarPorVino(nombreVino);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }
}
