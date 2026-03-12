package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Proveedor;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.ProveedorService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proveedores")
public class ProveedorController extends AbstractRestController {

  private final ProveedorService proveedorService;

  // Registrar proveedor
  @PostMapping
  public ResponseEntity<ResponseVO> registrarProveedor(@RequestBody Proveedor proveedor) {

    ResultadoSP resultado = proveedorService.registrarProveedor(proveedor);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Actualizar proveedor
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarProveedor(
      @PathVariable Integer id,
      @RequestBody Proveedor proveedor) {
    proveedor.setIdProveedor(id);
    ResultadoSP resultado = proveedorService.actualizarProveedor(proveedor);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Dar de baja/alta a proveedor
  @PatchMapping("/{idProveedor}/estado")
  public ResponseEntity<ResponseVO> cambiarEstadoProveedor(
      @PathVariable Integer idProveedor,
      @RequestParam("activo") boolean activo) {

    ResultadoSP resultado;
    if (activo) {
      resultado = proveedorService.darDeAltaProveedor(idProveedor);
    } else {
      resultado = proveedorService.darDeBajaProveedor(idProveedor);
    }

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Filtrar por termino de búsqueda a provedor
  @GetMapping("/filtrar")
  public ResponseEntity<ResponseVO> filtrarProveedor(@RequestParam("termino") String terminoBusqueda) {
    ResultadoSP resultado = proveedorService.filtrarProveedor(terminoBusqueda);
    return resultado.esExitoso()
        ? ok("Proveedores filtrados", resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Listar proveedores
  @GetMapping
  public ResponseEntity<ResponseVO> listarProveedores(){
    return ok(proveedorService.listarProveedores());
  }
}
