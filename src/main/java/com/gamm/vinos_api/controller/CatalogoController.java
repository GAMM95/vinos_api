package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.service.CatalogoService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

  private final CatalogoService catalogoService;

  // Registrar catálogo
  @PostMapping
  public ResponseVO registrarCatalogo(@RequestBody Catalogo catalogo) {
   ResultadoSP resultado = catalogoService.registrarCatalogo(catalogo);
   if (resultado.getCodigoRespuesta() == 1) {
     return new ResponseVO(true, resultado.getMensaje(), catalogo);
   } else {
     return ResponseVO.error(resultado.getMensaje());
   }
  }

  // Actualizar catálogo
  @PutMapping("/{id}")
  public ResponseVO actualizarCatalogo(@PathVariable Integer id, @RequestBody Catalogo catalogo) {
    catalogo.setIdCatalogo(id);
    ResultadoSP resultado = catalogoService.actualizarCatalogo(catalogo);
    if (resultado.getCodigoRespuesta() == 1) {
      return new ResponseVO(true, resultado.getMensaje(), catalogo);
    } else {
      return ResponseVO.error(resultado.getMensaje());
    }
  }

  @GetMapping
  public ResponseVO listarCatalogos() {
    return new ResponseVO(true, "Listado de catálogos", catalogoService.listarCatalogos());
  }

  @GetMapping("/filtrar")
  public ResponseVO filtrarPorProveedor(@RequestParam Integer idProveedor) {
    return new ResponseVO(true, "Catálogos filtrados por proveedor", catalogoService.filtrarPorProveedor(idProveedor));
  }
}


