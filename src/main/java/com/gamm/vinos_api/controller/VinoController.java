package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.security.annotations.Publico;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.VinoService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vinos")
@RequiredArgsConstructor
public class VinoController extends AbstractRestController {

  @Autowired
  private VinoService vinoService;

  // Registrar vino
  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> registrarVino(@RequestBody Vino vino) {
    ResultadoSP resultado = vinoService.registrarVino(vino);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Actualizar vinos
  @PutMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> actualizarVino(@PathVariable Integer id, @RequestBody Vino vino) {
    vino.setIdVino(id);
    ResultadoSP resultado = vinoService.actualizarVino(vino);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Eliminar vino
  @PatchMapping("/{id}/eliminar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> eliminarVino(@PathVariable Integer id) {
    ResultadoSP resultado = vinoService.eliminarVinoPorId(id);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Buscar vinos por nombre
  @GetMapping("/filtrar")
  @Publico
  public ResponseEntity<ResponseVO> filtrarVinoPorNombre(@RequestParam String nombre) {
    ResultadoSP resultado = vinoService.filtrarVinoPorNombre(nombre);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Listar vinos
  @GetMapping
  @Publico
  public ResponseEntity<ResponseVO> listarVinos() {
    return ok(vinoService.listarVinos());
  }

  // Listar vinos paginados
  @GetMapping("/paginado")
  @Publico
  public ResponseEntity<ResponseVO> listarVinosPaginados(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = vinoService.listarVinosPaginados(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Listar vinos disponibles para compra
  @GetMapping("/compra")
  @Publico
  public ResponseEntity<ResponseVO> listarVinosParaCompra() {
    return ok(vinoService.listarVinosParaCompra());
  }

  // Listar vinos disponibles para compra paginados
  @GetMapping("/compra/paginado")
  @Publico
  public ResponseEntity<ResponseVO> listarVinosParaCompraPaginados(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = vinoService.listarVinosParaCompraPaginados(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Filtrar vinos para compra (Tipo 5)
  @GetMapping("/compra/filtrar")
  @Publico
  public ResponseEntity<ResponseVO> filtrarVinosParaCompra(
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) String proveedores,       // ej: "1,2"
      @RequestParam(required = false) String categorias,        // ej: "5,7"
      @RequestParam(required = false) String presentaciones,    // ej: "1,6"
      @RequestParam(required = false) String tiposVino,         // ej: "2,3"
      @RequestParam(required = false) String origenVino         // ej: "Cascas"
  ) {
    ResultadoSP resultado = vinoService.filtrarVinosParaCompra(
        nombre, proveedores, categorias, presentaciones, tiposVino, origenVino
    );

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }


  // Filtrar vinos para compra paginados
  @GetMapping("/compra/filtrar/paginado")
  @Publico
  public ResponseEntity<ResponseVO> filtrarVinosParaCompraPaginados(
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) String proveedores,       // ej: "1,2"
      @RequestParam(required = false) String categorias,        // ej: "5,7"
      @RequestParam(required = false) String presentaciones,    // ej: "1,6"
      @RequestParam(required = false) String tiposVino,         // ej: "2,3"
      @RequestParam(required = false) String origenVino,        // ej: "Cascas"
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = vinoService.filtrarVinosParaCompraPaginados(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino, pagina, limite);
    return ResponseEntity.ok(response);
  }

}
