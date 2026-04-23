package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.service.CategoriaService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Categorías", description = "Gestión de categorías de vinos")
@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController extends AbstractRestController {

  private final CategoriaService categoriaService;

  @Operation(summary = "Registrar categoría")
  @PostMapping
  public ResponseEntity<ResponseVO> registrarCategoria(
      @Valid @RequestBody Categoria categoria
  ) {
    ResultadoSP resultado = categoriaService.registrarCategoria(categoria);
    return created(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Actualizar categoría")
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarCategoria(
      @PathVariable Integer id,
      @Valid @RequestBody Categoria categoria
  ) {
    categoria.setIdCategoria(id);
    ResultadoSP resultado = categoriaService.actualizarCategoria(categoria);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Cambiar estado de categoría")
  @PatchMapping("/{id}/estado")
  public ResponseEntity<ResponseVO> cambiarEstado(@PathVariable Integer id) {
    ResultadoSP resultado = categoriaService.cambiarEstado(id);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Listar categorías")
  @GetMapping
  public ResponseEntity<ResponseVO> listarCategorias() {
    return ok(categoriaService.listarCategorias());
  }
}
