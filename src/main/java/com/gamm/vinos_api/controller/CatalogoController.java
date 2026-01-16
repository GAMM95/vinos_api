package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.security.annotations.Publico;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.CatalogoService;
import com.gamm.vinos_api.utils.ResultadoSP;
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

}