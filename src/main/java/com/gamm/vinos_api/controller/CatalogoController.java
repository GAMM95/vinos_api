package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.security.annotations.Publico;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.CatalogoService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController extends AbstractRestController {

  private final CatalogoService catalogoService;

  // Registrar catálogo
  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> registrarCatalogo(@RequestBody Catalogo catalogo) {
    ResultadoSP resultado = catalogoService.registrarCatalogo(catalogo);

    return resultado.esExitoso()
        ? created(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Actualizar catálogo
  @PutMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> actualizarCatalogo(
      @PathVariable Integer id,
      @RequestBody Catalogo catalogo) {

    catalogo.setIdCatalogo(id);
    ResultadoSP resultado = catalogoService.actualizarCatalogo(catalogo);
    return resultado.esExitoso()
        ? created(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Dar de baja/alta a catálogo
  @PatchMapping("/{idCatalogo}/estado")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> cambiarEstado(
      @PathVariable Integer idCatalogo,
      @RequestParam("activo") boolean activo) {

    ResultadoSP resultado;
    if (activo) {
      resultado = catalogoService.darDeAltaCatalogo(idCatalogo);
    } else {
      resultado = catalogoService.darDeBajaCatalogo(idCatalogo);
    }

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  @GetMapping("/filtrar")
  @Publico
  public ResponseEntity<ResponseVO> filtrarPorProveedor(@RequestParam Integer idProveedor) {
    ResultadoSP resultado = catalogoService.filtrarPorProveedor(idProveedor);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  @GetMapping
  @Publico
  public ResponseEntity<ResponseVO> listarCatalogos() {
    return ok(catalogoService.listarCatalogos());
  }

  @GetMapping("/paginado")
  @Publico
  public ResponseEntity<ResponseVO> listarCatalogoProveedorPaginado(
      @RequestParam Integer idProveedor,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {

    // Llamada al servicio
    ResponseVO response = catalogoService.listarCatalogosPaginadosPorProveedor(idProveedor, pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Tipo 6 → proveedor + término
//  @GetMapping("/buscar")
//  @Publico
//  public ResponseEntity<ResponseVO> filtrarPorProveedorYTermino(
//      @RequestParam Integer idProveedor,
//      @RequestParam String termino) {
//    ResultadoSP resultado =
//        catalogoService.filtrarPorProveedorYTermino(idProveedor, termino);
//    return resultado.esExitoso()
//        ? ok(resultado.getMensaje(), resultado.getData())
//        : badRequest(resultado.getMensaje());
//  }
}