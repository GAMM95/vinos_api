package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.request.UsernameCheckRequest;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
  public ResponseEntity<ResponseVO> activarUsuario(
      @PathVariable Integer id,
      @RequestParam(required = false) Integer idSucursal) {

    ResultadoSP resultado = usuarioService.activarUsuario(id, idSucursal);
    return resultado.esExitoso()
        ? ResponseEntity.ok(new ResponseVO(true, resultado.getMensaje(), null))
        : ResponseEntity.badRequest().body(new ResponseVO(false, resultado.getMensaje(), null));
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

  // Actualizar usuario
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarUsuario(
      @PathVariable Integer id,
      @RequestBody Usuario usuario) {

    usuario.setIdUsuario(id);
    ResultadoSP resultado = usuarioService.actualizarUsuario(usuario);
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

  // Listar usuarios con paginación
  @GetMapping("/paginado")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarUsuariosPaginados(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    // Llamada al servicio
    ResponseVO response = usuarioService.listarUsuariosPaginados(pagina, limite);
    return ResponseEntity.ok(response);
  }


  @PostMapping("/{id}/foto")
  public ResponseEntity<ResponseVO> actualizarFoto(
      @PathVariable Integer id,
      @RequestParam("foto") MultipartFile foto) {
    ResultadoSP resultado = usuarioService.actualizarFoto(id, foto);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  @PostMapping("/verificar-username")
  public ResponseEntity<ResponseVO> verificarUsername(
      @RequestBody UsernameCheckRequest request) {

    ResultadoSP resultado = usuarioService.verificarUsername(request.username(), request.idUsuario());
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  /* Obtener usuario por su id */
  @GetMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> obtenerUsuarioPorId(@PathVariable Integer id) {
    Usuario usuario = usuarioService.obtenerPorId(id);
    if (usuario == null) {
      return badRequest("Usuario no encontrado");
    }
    return ok("Usuario obtenido correctamente", usuario);
  }
}
