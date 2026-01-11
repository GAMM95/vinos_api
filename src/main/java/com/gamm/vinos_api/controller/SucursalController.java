package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.SucursalService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sucursales")
public class SucursalController extends AbstractRestController {

  private final SucursalService sucursalService;

  // Listar sucursales
  @GetMapping
  public ResponseEntity<ResponseVO> listarSucursales() {
    return ok(sucursalService.listarSucursales());
  }

  // Registrar sucursales
  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> registrarSucursal(@RequestBody Sucursal sucursal) {
    ResultadoSP resultado = sucursalService.registrarSucursal(sucursal);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Actualizar sucursales
  @PutMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> actualizarSucursal(@PathVariable Integer id, @RequestBody Sucursal sucursal) {
    sucursal.setIdSucursal(id);
    ResultadoSP resultado = sucursalService.actualizarSucursal(sucursal);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Dar de baja / Dar de alta a sucursal
  @PatchMapping("/{id}/estado")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> cambiarEstado(
      @PathVariable Integer id,
      @RequestParam("abierto") boolean abierto) {
    ResultadoSP resultado;
    if (abierto) {
      resultado = sucursalService.darDeAltaSucursal(id);
    } else {
      resultado = sucursalService.darDeBajaSucursal(id);
    }
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // filtrar sucursal por nombre
  @GetMapping("/filtrar")
  public ResponseEntity<ResponseVO> filtrarSucursalPorNombre(
      @RequestParam String nombre) {
    ResultadoSP resultado = sucursalService.filtrarSucursal(nombre);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

}
