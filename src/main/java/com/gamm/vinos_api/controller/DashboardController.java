package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dashboard", description = "Resumen de actividad por rol")
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController extends AbstractRestController {

  private final DashboardService dashboardService;

  @Operation(summary = "Dashboard del vendedor")
  @GetMapping("/vendedor")
  @SoloVendedor
  public ResponseEntity<ResponseVO> getDashboardUser() {
    return ok(dashboardService.getDashboardUser());
  }

  @Operation(summary = "Dashboard del administrador")
  @GetMapping("/administrador")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> getDashboardAdmin() {
    return ok(dashboardService.getDashboardAdmin());
  }
}