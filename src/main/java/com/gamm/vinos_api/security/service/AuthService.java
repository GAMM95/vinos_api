package com.gamm.vinos_api.security.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.security.jwt.JwtUtil;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

  //  private final AuthenticationManager am;
  private final JwtUtil jwtUtil;
  private final UsuarioService usuarioService;
  private final PasswordEncoder passwordEncoder;

  public ResultadoSP login(String username, String password) {
    ResultadoSP resultado = usuarioService.login(username);
    Usuario usuario = (Usuario) resultado.getData();

    if (!resultado.esExitoso() || usuario == null)
      return new ResultadoSP(0, resultado.getMensaje(), null);

    if (!passwordEncoder.matches(password, usuario.getPassword()))
      return new ResultadoSP(0, "Credenciales incorrectas.", null);

    usuario.setPassword(null);
    Map<String, Object> data = Map.of(
        "usuario", usuario,
        "accessToken", jwtUtil.generarToken(usuario.getUsername()),
        "refreshToken", jwtUtil.generarRefreshToken(usuario.getUsername())
    );
    return new ResultadoSP(1, resultado.getMensaje(), data);
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
