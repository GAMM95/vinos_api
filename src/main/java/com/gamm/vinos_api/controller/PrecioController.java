package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.dto.view.PrecioDTO;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.PrecioService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

  @Operation(
      summary = "Listar todos los precios del stock",
      description = "Retorna todos los precios registrados en el stock. Solo administradores."
  )
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalPreciosStock() {
    List<PrecioDTO> lista = precioService.listarTotalPreciosStock();
    return ok(lista);
  }

  @Operation(
      summary = "Listar precios de la sucursal autenticada",
      description = "Retorna los precios del stock correspondientes a la sucursal del vendedor actualmente autenticado."
  )
  @GetMapping("/mi-sucursal")
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarPreciosSucursal() {
    List<PrecioDTO> lista = precioService.listarPreciosStockSucursal();
    return ok(lista);
  }

  @Operation(
      summary = "Filtrar precios por vino o sucursal",
      description = "Permite filtrar los precios del stock por nombre de vino y/o ID de sucursal. Solo administradores."
  )
  @GetMapping("/filtro")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarPorVinoOSucursal(
      @Parameter(description = "Nombre del vino a filtrar (opcional)")
      @RequestParam(required = false) String nombreVino,
      @Parameter(description = "ID de la sucursal a filtrar (opcional)")
      @RequestParam(required = false) Integer idSucursal
  ) {
    ResultadoSP resultado = precioService.filtrarPorVinoOSucursal(nombreVino, idSucursal);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(
      summary = "Ver historial de precios de un vino en una sucursal",
      description = "Retorna el historial de precios de un vino específico en una sucursal determinada."
  )
  @GetMapping("/historial")
  public ResponseEntity<ResponseVO> listarDetallePrecios(
      @Parameter(description = "ID del vino", required = true)
      @RequestParam Integer idVino,
      @Parameter(description = "ID de la sucursal", required = true)
      @RequestParam Integer idSucursal
  ) {
    return ok(precioService.listarPreciosDetalle(idVino, idSucursal));
  }

  @Operation(
      summary = "Filtrar historial de precios por nombre de vino",
      description = "Retorna el historial de precios filtrando por nombre de vino."
  )
  @GetMapping("/historial/filtro")
  public ResponseEntity<ResponseVO> filtrarHistorialPorVino(
      @Parameter(description = "Nombre del vino a filtrar", required = true)
      @RequestParam String nombreVino
  ) {
    ResultadoSP resultado = precioService.filtrarPorVino(nombreVino);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  // ─── Mutaciones ───────────────────────────────────────────────────────────

  @Operation(
      summary = "Asignar precio a un vino en una sucursal",
      description = "Crea un nuevo registro de precio para un vino en una sucursal determinada."
  )
  @PostMapping
  public ResponseEntity<ResponseVO> asignarPrecio(
      @Valid @RequestBody PrecioSucursal precio
  ) {
    ResultadoSP resultado = precioService.asignarPrecio(precio);
    return created(resultado.getMensaje(), resultado.getData());
  }

}