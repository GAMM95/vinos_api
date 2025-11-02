package com.gamm.vinos_api.security.service;


import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.security.jwt.JwtUtil;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager am;
  private final JwtUtil jwtUtil;
  private final UsuarioService usuarioService;
  private final PasswordEncoder passwordEncoder;

//  public Map<String, String> login(String username, String password) {
//    try {
//      am.authenticate(
//          new UsernamePasswordAuthenticationToken(username, password)
//      );
//    } catch (AuthenticationException ex) {
//      throw new BadCredentialsException("Usuario o contraseña incorrectos s");
//    }
//
//    // Genera tokens
//    String accessToken = jwtUtil.generarToken(username);
//    String refreshToken = jwtUtil.generarRefreshToken(username);
//
//    return Map.of(
//        "accessToken", accessToken,
//        "refreshToken", refreshToken
//    );
//  }

  public Map<String, Object> login(String username, String password) {
    ResultadoSP resultado = usuarioService.login(username);
    Usuario usuario = (Usuario) resultado.getData();

    if (usuario == null) {
      throw new BadCredentialsException(resultado.getMensaje());
    }

    // Validar password usando Spring Security
    if (!passwordEncoder.matches(password, usuario.getPassword())) {
      throw new BadCredentialsException("Nombre de usuario o contraseña incorrectos");
    }

    // Generar tokens
    String accessToken = jwtUtil.generarToken(usuario.getUsername());
    String refreshToken = jwtUtil.generarRefreshToken(usuario.getUsername());

    // Ocultar password antes de devolver
    usuario.setPassword(null);

    return Map.of(
        "accessToken", accessToken,
        "refreshToken", refreshToken,
        "mensaje", resultado.getMensaje(),
        "usuario", usuario
    );
  }


  public String refreshToken(String refreshToken) {
    try {
      String username = jwtUtil.obtenerUsername(refreshToken);
      return jwtUtil.generarToken(username); // Nuevo access token
    } catch (Exception e) {
      throw new BadCredentialsException("Refresh token inválido o expirado");
    }
  }

}
