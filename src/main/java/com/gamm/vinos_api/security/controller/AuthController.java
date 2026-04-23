package com.gamm.vinos_api.security.controller;

import com.gamm.vinos_api.controller.AbstractRestController;
import com.gamm.vinos_api.domain.model.Persona;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.dto.*;
import com.gamm.vinos_api.security.service.AuthService;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Autenticación", description = "Registro, login y gestión de contraseñas")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends AbstractRestController {

  private final UsuarioService usuarioService;
  private final AuthService authService;

  // ─── Registro ─────────────────────────────────────────────────────────────

  @Operation(summary = "Registrar nuevo usuario vendedor")
  @PostMapping("/registro")
  public ResponseEntity<ResponseVO> registrar(@Valid @RequestBody RegisterRequest req) {
    Persona p = new Persona();
    p.setNombres(req.nombres());
    p.setApellidoPaterno(req.apellidoPaterno());
    p.setApellidoMaterno(req.apellidoMaterno());
    p.setEmail(req.email());

    Usuario u = new Usuario();
    u.setPersona(p);
    u.setUsername(req.username());
    u.setPassword(req.password());

    // registrarUsuario lanza BusinessException si el SP retorna pRespuesta=0
    ResultadoSP r = usuarioService.registrarUsuario(u);
    return created(r.getMensaje(), r.getData());
  }

  // ─── Login ────────────────────────────────────────────────────────────────

  @Operation(summary = "Iniciar sesión")
  @PostMapping("/sesion")
  public ResponseEntity<ResponseVO> login(@Valid @RequestBody LoginRequest req) {
    // lanza BusinessException si el usuario no existe, está inactivo o credenciales incorrectas
    Map<String, Object> data = authService.login(req.username(), req.password());
    return ok(data);
  }

  // ─── Refresh token ────────────────────────────────────────────────────────

  @Operation(summary = "Renovar access token usando refresh token")
  @PostMapping("/token/refresh")
  public ResponseEntity<ResponseVO> refresh(@RequestBody Map<String, String> body) {
    String newAccessToken = authService.refreshToken(body.get("refreshToken"));
    return ok(new AuthResponse(newAccessToken, body.get("refreshToken"), "OK"));
  }

  // ─── Perfil ───────────────────────────────────────────────────────────────

  @Operation(summary = "Obtener perfil del usuario autenticado")
  @GetMapping("/usuarios/me")
  public ResponseEntity<ResponseVO> obtenerPerfil() {
    return ok(authService.obtenerPerfil());
  }

  // ─── Contraseñas ──────────────────────────────────────────────────────────

  @Operation(summary = "Restablecer contraseña de un usuario (solo administrador)")
  @SoloAdministrador
  @PatchMapping("/usuarios/{id}/password")
  public ResponseEntity<ResponseVO> resetearPassword(
      @PathVariable Integer id,
      @Valid @RequestBody ResetPasswordRequest req
  ) {
    ResultadoSP resultado = usuarioService.resetearPassword(id, req.nuevaPassword());
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Cambiar contraseña del usuario autenticado")
  @PatchMapping("/usuarios/me/password")
  public ResponseEntity<ResponseVO> cambiarPassword(@Valid @RequestBody ChangePasswordRequest req) {
    usuarioService.cambiarPassword(req.actual(), req.nueva());
    return ok("Contraseña actualizada correctamente.", null);
  }

  @Operation(summary = "Solicitar email de recuperación de contraseña")
  @PostMapping("/password/recuperacion")
  public ResponseEntity<ResponseVO> solicitarRecuperacion(@Valid @RequestBody EmailRequest request) {
    authService.generarTokenRecuperacion(request.email());
    return ok("Se envió un email con instrucciones.", null);
  }

  @Operation(summary = "Restaurar contraseña usando token recibido por email")
  @PostMapping("/password/restauracion")
  public ResponseEntity<ResponseVO> restaurarPassword(@Valid @RequestBody ResetPasswordRequestEmail request) {
    authService.resetearPasswordConToken(request.token(), request.nuevaPassword());
    return ok("Contraseña actualizada correctamente.", null);
  }
}