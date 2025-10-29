package com.gamm.vinos_api.controller;


import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.service.CategoriaService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // Registrar categoría
    @PostMapping
    public ResponseVO registrarCategoria(@RequestBody Categoria categoria) {
        ResultadoSP resultado = categoriaService.registrarCategoria(categoria);

        if (resultado.getCodigoRespuesta() == 1) {
            // Devuelve mensaje del SP + objeto creado
            return new ResponseVO(true, resultado.getMensaje(), categoria);
        } else {
            return ResponseVO.error(resultado.getMensaje());
        }
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseVO actualizarCategoria(
            @PathVariable Integer id,
            @RequestBody Categoria categoria) {
        categoria.setIdCategoria(id);
        ResultadoSP resultado = categoriaService.actualizarCategoria(categoria);

        if (resultado.getCodigoRespuesta() == 1) {
            return new ResponseVO(true, resultado.getMensaje(), categoria);
        } else {
            return ResponseVO.error(resultado.getMensaje());
        }
    }

    // Cambiar estado de categoría (activar / inactivar)
    @PatchMapping("/{id}/estado")
    public ResponseVO cambiarEstado(@PathVariable Integer id) {
        ResultadoSP resultado = categoriaService.cambiarEstado(id);

        if (resultado.getCodigoRespuesta() == 1) {
            return new ResponseVO(true, resultado.getMensaje(), null);
        } else {
            return ResponseVO.error(resultado.getMensaje());
        }
    }

    // Listar categorías
    @GetMapping
    public ResponseVO listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();
        return ResponseVO.success(categorias);
    }

    // Listar en combo de categorías
    @GetMapping("/combo")
    public ResponseVO comboCategorias() {
        List<Categoria> categorias = categoriaService.comboCategoria();
        return ResponseVO.success(categorias);
    }
}
