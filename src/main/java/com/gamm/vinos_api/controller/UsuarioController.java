package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.request.UsernameCheckRequest;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.exception.business.BusinessException;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController extends AbstractRestController {

  private final UsuarioService usuarioService;

  // ─── Consultas ────────────────────────────────────────────────────────────

  @Operation(summary = "Listar todos los usuarios")
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarUsuarios() {
    return ok(usuarioService.listarUsuarios());
  }

  @Operation(summary = "Listar usuarios con paginación")
  @GetMapping("/paginado")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarUsuariosPaginados(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    return ResponseEntity.ok(usuarioService.listarUsuariosPaginados(pagina, limite));
  }

  @Operation(summary = "Obtener usuario por ID")
  @GetMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> obtenerPorId(@PathVariable Integer id) {
    Usuario usuario = usuarioService.obtenerPorId(id);
    // obtenerPorId retorna null si no existe — lanzamos excepción manualmente
    // ya que este método no pasa por ResultadoSP
    if (usuario == null) {
      throw new BusinessException("El usuario especificado no existe.");
    }
    return ok(usuario);
  }

  @Operation(summary = "Filtrar usuarios por término de búsqueda")
  @GetMapping("/filtro")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarUsuarios(@RequestParam String termino) {
    ResultadoSP resultado = usuarioService.filtrarUsuario(termino);
    ResponseVO.validar(resultado); // → "Debe enviar un término de búsqueda."
    return ok(resultado.getMensaje(), resultado.getData());
  }

  // ─── Mutaciones ───────────────────────────────────────────────────────────

  @Operation(summary = "Actualizar datos del usuario")
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarUsuario(
      @PathVariable Integer id,
      @Valid @RequestBody Usuario usuario
  ) {
    usuario.setIdUsuario(id);
    ResultadoSP resultado = usuarioService.actualizarUsuario(usuario);
    ResponseVO.validar(resultado); // → "El usuario no existe." / "No se detectaron cambios."
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Inactivar usuario")
  @PatchMapping("/{id}/inactivacion")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> inactivarUsuario(@PathVariable Integer id) {
    ResultadoSP resultado = usuarioService.inactivarUsuario(id);
    ResponseVO.validar(resultado); // → "Usuario no encontrado o ya está inactivo."
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Activar usuario y asignar sucursal")
  @PatchMapping("/{id}/activacion")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> activarUsuario(
      @PathVariable Integer id,
      @RequestParam(required = false) Integer idSucursal
  ) {
    ResultadoSP resultado = usuarioService.activarUsuario(id, idSucursal);
    ResponseVO.validar(resultado); // → "Debe enviarse un ID." / "La sucursal no existe." / etc.
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Actualizar foto del usuario")
  @PostMapping("/{id}/foto")
  public ResponseEntity<ResponseVO> actualizarFoto(
      @PathVariable Integer id,
      @RequestParam("foto") MultipartFile foto
  ) {
    ResultadoSP resultado = usuarioService.actualizarFoto(id, foto);
    ResponseVO.validar(resultado); // → "El usuario no existe." / "No se envió ninguna foto."
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Verificar disponibilidad de username en tiempo real")
  @PostMapping("/username/verificacion")
  public ResponseEntity<ResponseVO> verificarUsername(
      @RequestBody UsernameCheckRequest request
  ) {
    ResultadoSP resultado = usuarioService.verificarUsername(request.username(), request.idUsuario());
    ResponseVO.validar(resultado); // → "El username ya está siendo utilizado."
    return ok(resultado.getMensaje(), null);
  }
}