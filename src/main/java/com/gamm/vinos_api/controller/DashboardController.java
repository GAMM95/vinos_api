package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Endpoints del dashboard del usuario")
public class DashboardController extends AbstractRestController {

  private final DashboardService dashboardService;

  @Operation(summary = "Obtener dashboard del usuario")
  @ApiResponse(
      responseCode = "200",
      description = "Retorna resumen, totales, litros y ventas anuales del usuario logueado",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ResponseVO.class)
      )
  )
  @GetMapping("/user")
  @SoloVendedor
  public ResponseEntity<ResponseVO> getDashboardUser() {
    return ok(dashboardService.getDashboardUser());
  }


  @Operation(summary = "Obtener dashboard del administrador")
  @ApiResponse(
      responseCode = "200",
      description = "Retorna resumen, totales, litros y ventas anuales para el administrador",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ResponseVO.class)
      )
  )
  @GetMapping("/admin")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> getDashboardAdmin() {
    return ok(dashboardService.getDashboardAdmin());
  }
}