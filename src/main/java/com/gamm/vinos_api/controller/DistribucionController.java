package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.DistribucionService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/distribucion")
@RequiredArgsConstructor
public class DistribucionController extends AbstractRestController {
  private final DistribucionService distribucionService;

  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> distribuirProducto
      (@RequestBody DistribucionSucursal distribucionSucursal) {
    ResultadoSP resultadoSP = distribucionService.distribuirProducto(distribucionSucursal);
    return resultadoSP.esExitoso()
        ? created(resultadoSP.getMensaje(), resultadoSP.getData())
        : badRequest(resultadoSP.getMensaje());
  }

  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarRepartoSucursal(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = distribucionService.listarRepartoSucursal(pagina, limite);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/filtrar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarReportesPorSucursalORango(
      @RequestParam(required = false) Integer idSucursal,
      @RequestParam(required = false) LocalDate fechaInicio,
      @RequestParam(required = false) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = distribucionService.filtrarRepartosPorSucursalORango(idSucursal, fechaInicio, fechaFin, pagina, limite);
    return ResponseEntity.ok(response);
  }
}
