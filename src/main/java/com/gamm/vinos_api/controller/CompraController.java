package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController extends AbstractRestController {
  private final CompraService compraService;

  @PostMapping
  public ResponseEntity<ResponseVO> crearCompra(@RequestBody Compra compra) {
    ResultadoSP res = compraService.crearCompra(compra);
    return res.esExitoso() ? ok(res.getMensaje(), null) : badRequest(res.getMensaje());
  }

  @PutMapping("/{id}/confirmar")
  public ResponseEntity<ResponseVO> confirmarCompra(@PathVariable Integer id) {
    Compra compra = new Compra();
    compra.setIdCompra(id);
    ResultadoSP res = compraService.confirmarCompra(compra);
    return res.esExitoso() ? ok(res.getMensaje(), null) : badRequest(res.getMensaje());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarCompra(@PathVariable Integer id, @RequestBody Compra compra) {
    compra.setIdCompra(id);
    ResultadoSP res = compraService.actualizarCompra(compra);
    return res.esExitoso() ? ok(res.getMensaje(), null) : badRequest(res.getMensaje());
  }

  @PatchMapping("/{id}/anular")
  public ResponseEntity<ResponseVO> anularCompra(@PathVariable Integer id) {
    ResultadoSP res = compraService.anularCompra(id);
    return res.esExitoso() ? ok(res.getMensaje(), null) : badRequest(res.getMensaje());
  }

  @PatchMapping("/{id}/reactivar")
  public ResponseEntity<ResponseVO> reactivarCompra(@PathVariable Integer id) {
    ResultadoSP res = compraService.reactivarCompra(id);
    return res.esExitoso() ? ok(res.getMensaje(), null) : badRequest(res.getMensaje());
  }

  @GetMapping("/buscar")
  public ResponseEntity<ResponseVO> buscarCompras(
      @RequestParam(required = false) String nombreProveedor,
      @RequestParam(required = false) LocalDate fechaInicio,
      @RequestParam(required = false) LocalDate fechaFin) {

    Compra filtro = new Compra();
    filtro.setNombreProveedor(nombreProveedor);
    filtro.setFechaInicio(fechaInicio);
    filtro.setFechaFin(fechaFin);

    ResultadoSP res = compraService.buscarCompras(filtro);
    return res.esExitoso() ? ok(res.getMensaje(), res.getData()) : badRequest(res.getMensaje());
  }

  @GetMapping
  public ResponseEntity<ResponseVO> listarCompras() {
    return ok(compraService.listarCompras());
  }
}
