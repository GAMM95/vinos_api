package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.dto.view.PrecioView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.PrecioService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Precios", description = "Gestión de precios por sucursal")
@RestController
@RequestMapping("/api/v1/precios")
@RequiredArgsConstructor
public class PrecioController extends AbstractRestController {

  private final PrecioService precioService;

  // ─── Consultas ────────────────────────────────────────────────────────────

  @Operation(summary = "Listar todos los precios del stock")
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalPreciosStock() {
    List<PrecioView> lista = precioService.listarTotalPreciosStock();
    return ok(lista);
  }

  // Listar precios de la sucursal autenticada (vendedor)
  @Operation(summary = "Listar precios de la sucursal autenticada")
  @GetMapping("/mi-sucursal")  //"Sucursal/actual"
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarPreciosSucursal() {
    List<PrecioView> lista = precioService.listarPreciosStockSucursal();
    return ok(lista);
  }

  // Filtrar por vino  o sucursal (admin)
  @Operation(summary = "Filtrar precios por vino o sucursal")
  @GetMapping("/filtrar") // "/filtro
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarPorVinoOSucursal(
      @RequestParam(required = false) String nombreVino,
      @RequestParam(required = false) Integer idSucursal
  ) {
    ResultadoSP resultado = precioService.filtrarPorVinoOSucursal(nombreVino, idSucursal);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  // Ver historial de precios de un  vino
  @Operation(summary = "Ver historial de precios de un vino en una sucursal")
  @GetMapping("/detalle") // "/historial
  public ResponseEntity<ResponseVO> listarDetallePrecios(
      @RequestParam Integer idVino,
      @RequestParam Integer idSucursal
  ) {
    List<PrecioView> lista = precioService.listarPreciosDetalle(idVino, idSucursal);
    return ok(lista);
  }

  // Filtrar historial por nombre de vino
  @Operation(summary = "Filtrar historial de precios por nombre de vino")
//  @GetMapping("/historial/filtro")
  @GetMapping("/filtrar-vino")
  public ResponseEntity<ResponseVO> filtrarHistorialPorVino(
      @RequestParam String nombreVino
  ) {
    ResultadoSP resultado = precioService.filtrarPorVino(nombreVino);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }
  // ─── Mutaciones ───────────────────────────────────────────────────────────

  @Operation(summary = "Asignar precio a un vino en una sucursal")
  @PostMapping
  public ResponseEntity<ResponseVO> asignarPrecio(
    @Valid @RequestBody PrecioSucursal precio
  ) {
    ResultadoSP resultado = precioService.asignarPrecio(precio);
    ResponseVO.validar(resultado);
    return created(resultado.getMensaje(), resultado.getData());
  }

}
