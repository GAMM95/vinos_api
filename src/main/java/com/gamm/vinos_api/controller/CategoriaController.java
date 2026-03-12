package com.gamm.vinos_api.controller;


import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.service.CategoriaService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController extends AbstractRestController {

  private final CategoriaService categoriaService;

  // Registrar categoría
  @PostMapping
  public ResponseEntity<ResponseVO> registrarCategoria(@RequestBody Categoria categoria) {
    ResultadoSP resultado = categoriaService.registrarCategoria(categoria);

    return resultado.esExitoso()
        ? created(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Actualizar categoría
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarCategoria(
      @PathVariable Integer id,
      @RequestBody Categoria categoria) {

    categoria.setIdCategoria(id);
    ResultadoSP resultado = categoriaService.actualizarCategoria(categoria);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), categoria)
        : badRequest(resultado.getMensaje());
  }

  // Cambiar estado de categoría (activar / inactivar)
  @PatchMapping("/{id}/estado")
  public ResponseEntity<ResponseVO> cambiarEstado(@PathVariable Integer id) {
    ResultadoSP resultado = categoriaService.cambiarEstado(id);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Listar categorías
  @GetMapping
  public ResponseEntity<ResponseVO> listarCategorias() {
    return ok(categoriaService.listarCategorias());
  }

  // Listar en combo de categorías
  @GetMapping("/combo")
  public ResponseEntity<ResponseVO> comboCategorias() {
    return ok(categoriaService.comboCategoria());
  }
}
