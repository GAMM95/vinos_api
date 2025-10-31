package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Catalogo;
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
  public ResponseEntity<ResponseVO> registrarCatalogo(@RequestBody Catalogo catalogo) {
    ResultadoSP resultado = catalogoService.registrarCatalogo(catalogo);

    return resultado.esExitoso()
        ? created(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Actualizar catálogo
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO>  actualizarCatalogo(
      @PathVariable Integer id,
      @RequestBody Catalogo catalogo) {

    catalogo.setIdCatalogo(id);
    ResultadoSP resultado = catalogoService.actualizarCatalogo(catalogo);
    return resultado.esExitoso()
        ? created(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  @GetMapping
  public ResponseEntity<ResponseVO> listarCatalogos() {
    return ok(catalogoService.listarCatalogos());
  }

  @GetMapping("/filtrar")
  public ResponseEntity<ResponseVO> filtrarPorProveedor(@RequestParam Integer idProveedor) {
    return ok(catalogoService.filtrarPorProveedor(idProveedor));
  }
}


