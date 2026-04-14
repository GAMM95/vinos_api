package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.security.annotations.Publico;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.VinoService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Vinos", description = "Gestión del catálogo de vinos")
@RestController
@RequestMapping("/api/v1/vinos")
@RequiredArgsConstructor
public class VinoController extends AbstractRestController {

  private final VinoService vinoService;

  // ─── Consultas ────────────────────────────────────────────────────────────

  @Operation(summary = "Listar todos los vinos")
  @GetMapping
  @Publico
  public ResponseEntity<ResponseVO> listarVinos() {
    return ok(vinoService.listarVinos());
  }

  @Operation(summary = "Listar vinos paginados")
  @GetMapping("/paginado")
  @Publico
  public ResponseEntity<ResponseVO> listarVinosPaginados(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = vinoService.listarVinosPaginados(pagina, limite);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Listar vinos disponibles para compra")
  @GetMapping("/compra")
  @Publico
  public ResponseEntity<ResponseVO> listarVinosParaCompra() {
    return ok(vinoService.listarVinosParaCompra());
  }

  @Operation(summary = "Listar vinos para compra paginados")
  @GetMapping("/compra/paginado")
  @Publico
  public ResponseEntity<ResponseVO> listarVinosParaCompraPaginados(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = vinoService.listarVinosParaCompraPaginados(pagina, limite);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Filtrar vinos para compra por múltiples criterios")
  @GetMapping("/compra/filtro")
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
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Filtrar vinos para compra por criterios — paginado")
  @GetMapping("/compra/filtro/paginado")
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

  @Operation(summary = "Filtrar vinos por nombre")
  @GetMapping("/filtrar")
  @Publico
  public ResponseEntity<ResponseVO> filtrarVinoPorNombre(@RequestParam String nombre) {
    ResultadoSP resultado = vinoService.filtrarVinoPorNombre(nombre);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  // ─── Mutaciones ────────────────────────────────────────────────────────────

  @Operation(summary = "Registrar nuevo vino")
  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> registrarVino(@Valid @RequestBody Vino vino) {
    ResultadoSP resultado = vinoService.registrarVino(vino);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Actualizar vino")
  @PutMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> actualizarVino(
      @PathVariable Integer id,
      @RequestBody Vino vino
  ) {
    vino.setIdVino(id);
    ResultadoSP resultado = vinoService.actualizarVino(vino);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Eliminar vino (baja lógica)")
  @PatchMapping("/{id}/eliminacion")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> eliminarVino(@PathVariable Integer id) {
    ResultadoSP resultado = vinoService.eliminarVinoPorId(id);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), null);
  }

}
