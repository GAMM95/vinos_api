package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/ventas")
@RequiredArgsConstructor
public class VentaController extends AbstractRestController{

  private final VentaService ventaService;

  // Listar productos del carrito del usuario logueado
  @GetMapping("/carrito-user")
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarCarritoVentaUser() {
    return ok(ventaService.listarCarritoVentaUsuario());
  }

  // Listar productos del carrito de cualquier usuario
  @GetMapping("/carrito")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarCarritoVenta(
      @RequestParam Integer idVenta
  ){
    return ok(ventaService.listarCarritoVentaAdmin(idVenta));
  }

}
