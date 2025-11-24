package com.gamm.vinos_api.security.controller;

import com.gamm.vinos_api.controller.AbstractRestController;
import com.gamm.vinos_api.domain.model.Persona;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.security.dto.*;
import com.gamm.vinos_api.security.service.AuthService;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
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

  @PostMapping("reset-password")
  public ResponseEntity<ResponseVO> resetPassword(@RequestBody ResetPasswordRequest req) {
    ResultadoSP resultado = usuarioService.resetearPassword(
        req.IdUsuario(),
        req.nuevaPassword()
    );

    return resultado.esExitoso()
        ? ok(resultado.getMensaje())
        : badRequest(resultado.getMensaje());
  }

  @PostMapping ("/change-password")
  public ResponseEntity<ResponseVO> changePassword (@RequestBody ChangePasswordRequest req){
    ResultadoSP resultado = usuarioService.cambiarPassword(
        req.IdUsuario(),
        req.actual(),
        req.nueva()
    );
    return resultado.esExitoso()
        ? ok(resultado.getMensaje())
        : badRequest(resultado.getMensaje());
  }

  @GetMapping("/me")
  public ResponseEntity<ResponseVO> obtenerPerfil(@RequestHeader("Authorization") String bearerToken) {
    ResultadoSP resultado = authService.obtenerPerfilDesdeToken(bearerToken);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

}
