package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.security.annotations.Publico;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.CatalogoService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Catálogos", description = "Gestión de catálogos por proveedor")
@RestController
@RequestMapping("/api/v1/catalogos")
@RequiredArgsConstructor
public class CatalogoController extends AbstractRestController {

  private final CatalogoService catalogoService;

  @Operation(summary = "Registrar catálogo")
  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> registrarCatalogo(@Valid @RequestBody Catalogo catalogo) {
    ResultadoSP resultado = catalogoService.registrarCatalogo(catalogo);
    ResponseVO.validar(resultado);
    return created(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Actualizar catálogo")
  @PutMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> actualizarCatalogo(
      @PathVariable Integer id,
      @Valid @RequestBody Catalogo catalogo
  ) {
    catalogo.setIdCatalogo(id);
    ResultadoSP resultado = catalogoService.actualizarCatalogo(catalogo);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData()); // ✅ ok, no created — es actualización
  }

  @Operation(summary = "Cambiar estado del catálogo")
  @PatchMapping("/{id}/estado")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> cambiarEstado(
      @PathVariable Integer id,
      @RequestParam boolean activo
  ) {
    ResultadoSP resultado = activo
        ? catalogoService.darDeAltaCatalogo(id)
        : catalogoService.darDeBajaCatalogo(id);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Listar catálogos")
  @GetMapping
  @Publico
  public ResponseEntity<ResponseVO> listarCatalogos() {
    return ok(catalogoService.listarCatalogos());
  }

  @Operation(summary = "Filtrar catálogos por proveedor")
  @GetMapping("/filtro") // ✅ /filtrar → /filtro
  @Publico
  public ResponseEntity<ResponseVO> filtrarPorProveedor(@RequestParam Integer idProveedor) {
    ResultadoSP resultado = catalogoService.filtrarPorProveedor(idProveedor);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Listar catálogos paginados por proveedor")
  @GetMapping("/paginado")
  @Publico
  public ResponseEntity<ResponseVO> listarCatalogosPaginados(
      @RequestParam Integer idProveedor,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(catalogoService.listarCatalogosPaginadosPorProveedor(idProveedor, pagina, limite)
    );
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