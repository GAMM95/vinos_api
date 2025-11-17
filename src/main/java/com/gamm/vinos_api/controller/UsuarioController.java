package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController extends AbstractRestController {
  private final UsuarioService usuarioService;

  // Inactivar usuario
  @PatchMapping("/{id}/inactivar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> inactivarUsuario(@PathVariable Integer id) {
    ResultadoSP resultado = usuarioService.inactivarUsuario(id);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Activar usuario
  @PatchMapping("/{id}/activar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> activarUsuario(@PathVariable Integer id) {
    ResultadoSP resultado = usuarioService.activarUsuario(id);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Filtrar usuarios
  @GetMapping("/filtrar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarUsuarios(@RequestParam String termino) {
    ResultadoSP resultado = usuarioService.filtrarUsuario(termino);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Listar usuarios
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarUsuarios() {
    return ok(usuarioService.listarUsuarios());
  }
}
