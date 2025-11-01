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
    String token = authService.login(req.username(), req.password());
    AuthResponse data = new AuthResponse(token, "OK");
    return ok(data);
  }
}
