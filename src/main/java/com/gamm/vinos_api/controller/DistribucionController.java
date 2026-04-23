package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.DistribucionService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Distribución", description = "Gestión de distribución de stock a sucursales")
@RestController
@RequestMapping("/api/v1/distribuciones")
@RequiredArgsConstructor
public class DistribucionController extends AbstractRestController {

  private final DistribucionService distribucionService;

  @Operation(summary = "Registrar distribución de producto a sucursal")
  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> distribuirProducto(
      @Valid @RequestBody DistribucionSucursal distribucionSucursal
  ) {
    ResultadoSP resultado = distribucionService.distribuirProducto(distribucionSucursal);
    return created(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Listar repartos a sucursales paginado")
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarRepartoSucursal(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return okPaginado(distribucionService.listarRepartoSucursal(pagina, limite));
  }

  @Operation(summary = "Filtrar repartos por sucursal y/o rango de fechas")
  @GetMapping("/filtro") // ✅ /filtrar → /filtro
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarReportesPorSucursalORango(
      @RequestParam(required = false) Integer idSucursal,
      @RequestParam(required = false) LocalDate fechaInicio,
      @RequestParam(required = false) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return okPaginado(distribucionService.filtrarRepartosPorSucursalORango(idSucursal, fechaInicio, fechaFin, pagina, limite));
  }
}
