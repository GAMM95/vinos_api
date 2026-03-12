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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends AbstractRestController {

  private final UsuarioService usuarioService;
  private final AuthService authService;

  @PostMapping("/registrar")
  public ResponseEntity<ResponseVO> registrar(@RequestBody RegisterRequest req) {
    Persona p = new Persona();
    p.setNombres(req.nombres());
    p.setApellidoPaterno(req.apellidoPaterno());
    p.setApellidoMaterno(req.apellidoMaterno());
    p.setEmail(req.email());

    Usuario u = new Usuario();
    u.setPersona(p);
    u.setUsername(req.username());
    u.setPassword(req.password());

    ResultadoSP r = usuarioService.registrarUsuario(u);

    return r.esExitoso()
        ? created(r.getMensaje(), r.getData())
        : badRequest(r.getMensaje());
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseVO> login(@RequestBody LoginRequest req) {
    ResultadoSP resultado = authService.login(req.username(), req.password());
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  @PostMapping("/refresh")
  public ResponseEntity<ResponseVO> refresh(@RequestBody Map<String, String> body) {
    String refreshToken = body.get("refreshToken");
    String newAccessToken = authService.refreshToken(refreshToken);
    AuthResponse data = new AuthResponse(newAccessToken, refreshToken, "OK");
    return ok(data);
  }

  // Resetear contraseña (admin)
  @PatchMapping("/password/reset/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> resetearPassword(
      @PathVariable Integer id,
      @RequestBody ResetPasswordRequest req
  ) {
    ResultadoSP resultado =
        usuarioService.resetearPassword(id, req.nuevaPassword());

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  @PatchMapping("/password")
  public ResponseEntity<ResponseVO> changePassword(
      @RequestBody ChangePasswordRequest req
  ) {
    ResultadoSP resultado = usuarioService.cambiarPassword(
        req.actual(),
        req.nueva()
    );
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  @GetMapping("/me")
  public ResponseEntity<ResponseVO> obtenerPerfil() {
    ResultadoSP resultado = authService.obtenerPerfilDesdeToken();
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  @PostMapping("/password/recuperar")
  public ResponseEntity<ResponseVO> solicitarRecuperacion(@Valid @RequestBody EmailRequest request) {
    String email = request.email();
    ResultadoSP resultado = authService.generarTokenRecuperacion(email);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null) // No enviamos el token al cliente
        : badRequest(resultado.getMensaje());
  }

  // Restaurar contraseña usando token
  @PostMapping("/password/reset/token")
  public ResponseEntity<ResponseVO> restaurarPassword(@Valid @RequestBody ResetPasswordRequestEmail request) {
    ResultadoSP resultado = authService.resetearPasswordConToken(request.token(), request.nuevaPassword());

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

}
