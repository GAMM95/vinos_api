package com.gamm.vinos_api.security.controller;

import com.gamm.vinos_api.controller.AbstractRestController;
import com.gamm.vinos_api.domain.model.Persona;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.security.service.AuthService;
import com.gamm.vinos_api.security.dto.AuthResponse;
import com.gamm.vinos_api.security.dto.LoginRequest;
import com.gamm.vinos_api.security.dto.RegisterRequest;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//  @PostMapping("/login")
//  public ResponseEntity<ResponseVO> login(@RequestBody LoginRequest req) {
//    var tokens = authService.login(req.username(), req.password());
//    AuthResponse data = new AuthResponse(tokens.get("accessToken"), tokens.get("refreshToken"), "OK");
//    return ok(data);
//  }
  @PostMapping("/login")
  public ResponseEntity<ResponseVO> login(@RequestBody LoginRequest req) {
    var data = authService.login(req.username(), req.password());
    return ok(data);
  }

  @PostMapping("/refresh")
  public ResponseEntity<ResponseVO> refresh(@RequestBody Map<String, String> body) {
    String refreshToken = body.get("refreshToken");
    String newAccessToken = authService.refreshToken(refreshToken);
    AuthResponse data = new AuthResponse(newAccessToken, refreshToken, "OK");
    return ok(data);
  }


}
