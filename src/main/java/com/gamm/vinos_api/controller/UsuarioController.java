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
    return okPaginado(usuarioService.listarUsuariosPaginados(pagina, limite));
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

  @Operation(summary = "Filtrar usuarios por término")
  @GetMapping("/filtro")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarUsuarios(@RequestParam String termino) {
    return ok(usuarioService.filtrarUsuario(termino)); // ✅ List directa
  }

  // ─── Mutaciones ───────────────────────────────────────────────────────────

  @Operation(summary = "Actualizar datos del usuario")
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarUsuario(
      @PathVariable Integer id,
      @Valid @RequestBody Usuario usuario
  ) {
    usuario.setIdUsuario(id);
    Usuario actualizado = usuarioService.actualizarUsuario(usuario); // ✅ retorna Usuario
    return ok("Datos actualizados correctamente.", actualizado);
  }

  @Operation(summary = "Inactivar usuario")
  @PatchMapping("/{id}/inactivacion")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> inactivarUsuario(@PathVariable Integer id) {
    usuarioService.inactivarUsuario(id); // ✅ void — lanza si falla
    return ok("Usuario inactivado correctamente.", null);
  }

  @Operation(summary = "Activar usuario y asignar sucursal")
  @PatchMapping("/{id}/activacion")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> activarUsuario(
      @PathVariable Integer id,
      @RequestParam(required = false) Integer idSucursal
  ) {
    usuarioService.activarUsuario(id, idSucursal); // ✅ void
    return ok("Usuario activado correctamente.", null);
  }

  @Operation(summary = "Actualizar foto del usuario")
  @PostMapping("/{id}/foto")
  public ResponseEntity<ResponseVO> actualizarFoto(
      @PathVariable Integer id,
      @RequestParam("foto") MultipartFile foto
  ) {
    ResultadoSP resultado = usuarioService.actualizarFoto(id, foto);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Verificar disponibilidad de username")
  @GetMapping("/username/disponibilidad")
  public ResponseEntity<ResponseVO> verificarUsername(
      @RequestParam String username,
      @RequestParam(required = false) Integer idUsuario
  ) {
    usuarioService.verificarUsername(username, idUsuario); // ✅ void — lanza si no disponible
    return ok("Username disponible.", null);
  }
}