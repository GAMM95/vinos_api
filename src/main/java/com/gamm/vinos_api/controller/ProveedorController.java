package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Proveedor;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.ProveedorService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Proveedores", description = "Gestión de proveedores")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/proveedores")
public class ProveedorController extends AbstractRestController {

  private final ProveedorService proveedorService;

  @Operation(summary = "Registrar proveedor")
  @PostMapping
  public ResponseEntity<ResponseVO> registrarProveedor(@Valid @RequestBody Proveedor proveedor) {
    ResultadoSP resultado = proveedorService.registrarProveedor(proveedor);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Actualizar proveedor")
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarProveedor(
      @PathVariable Integer id,
      @Valid @RequestBody Proveedor proveedor
  ) {
    proveedor.setIdProveedor(id);
    ResultadoSP resultado = proveedorService.actualizarProveedor(proveedor);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Cambiar estado del proveedor")
  @PatchMapping("/{id}/estado")
  public ResponseEntity<ResponseVO> cambiarEstado(
      @PathVariable Integer id,
      @RequestParam boolean activo
  ) {
    ResultadoSP resultado = activo
        ? proveedorService.darDeAltaProveedor(id)
        : proveedorService.darDeBajaProveedor(id);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Filtrar proveedores por término")
  @GetMapping("/filtro") // ✅ /filtrar → /filtro
  public ResponseEntity<ResponseVO> filtrarProveedor(@RequestParam String termino) {
    ResultadoSP resultado = proveedorService.filtrarProveedor(termino);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Listar proveedores")
  @GetMapping
  public ResponseEntity<ResponseVO> listarProveedores() {
    return ok(proveedorService.listarProveedores());
  }
}
