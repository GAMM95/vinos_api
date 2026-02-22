package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.CajaService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/caja")
@RequiredArgsConstructor
public class CajaController extends AbstractRestController {

  private final CajaService cajaService;

  // Abrir caja
  @PostMapping("/abrir")
  public ResponseEntity<ResponseVO> abrirCaja(@RequestBody Caja caja) {
    ResultadoSP resultadoSP = cajaService.abrirCaja(caja);
    return resultadoSP.esExitoso()
        ? created(resultadoSP.getMensaje(), resultadoSP.getData())
        : badRequest(resultadoSP.getMensaje());
  }

  // Cerrar caja
  @PostMapping("/cerrar/{idCaja}")
  public ResponseEntity<ResponseVO> cerrarCaja(@PathVariable Integer idCaja) {
    ResultadoSP resultado = cajaService.cerrarCaja(idCaja);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Listar mis cajas (paginado)
  @GetMapping("/mis-cajas")
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarMisCajas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = cajaService.listarMisCajas(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Listar todas las cajas (paginado)
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalCajas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = cajaService.listarTotalCajas(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Filtrar mis cajas por rango de fechas
  @GetMapping("/mis-cajas/filtrar")
  @SoloVendedor
  public ResponseEntity<ResponseVO> filtrarMisCajasPorRango(
      @RequestParam LocalDate fechaInicio,
      @RequestParam(required = false) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = cajaService.filtrarMisCajasPorRango(fechaInicio, fechaFin, pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Filtrar cajas por usuario y/o rango de fechas
  @GetMapping("/filtrar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarCajasPorUsuarioORango(
      @RequestParam(required = false) Integer idUsuario,
      @RequestParam(required = false) LocalDate fechaInicio,
      @RequestParam(required = false) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = cajaService.filtrarCajasPorUsuarioORango(idUsuario, fechaInicio, fechaFin, pagina, limite);
    return ResponseEntity.ok(response);
  }
}
