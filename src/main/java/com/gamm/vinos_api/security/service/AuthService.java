package com.gamm.vinos_api.security.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.security.jwt.JwtUtil;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtUtil jwtUtil;
  private final UsuarioService usuarioService;
  private final PasswordEncoder passwordEncoder;

  @Value("${fotosUsuarios.url}")
  private String fotosUsuariosUrl;

  private String buildFotoUrl(String rutaFoto) {
    if (rutaFoto == null || rutaFoto.trim().isEmpty()) {
      return null;
    }

    rutaFoto = rutaFoto.trim();

    // Si ya es URL completa, retornar tal cual
    if (rutaFoto.startsWith("http://") || rutaFoto.startsWith("https://")) {
      return rutaFoto;
    }

    // Si empieza con /FotosUsuarios/ evitar duplicar
    if (rutaFoto.startsWith("/FotosUsuarios/")) {
      rutaFoto = rutaFoto.substring("/FotosUsuarios/".length());
    }

    return fotosUsuariosUrl + "/FotosUsuarios/" + rutaFoto;
  }

  public ResultadoSP login(String username, String password) {
    ResultadoSP resultado = usuarioService.login(username);
    Usuario usuario = (Usuario) resultado.getData();

    if (!resultado.esExitoso() || usuario == null) {
      return new ResultadoSP(0, resultado.getMensaje(), null);
    }

    if (!passwordEncoder.matches(password, usuario.getPassword())) {
      return new ResultadoSP(0, "Credenciales incorrectas.", null);
    }

    usuario.setPassword(null);

    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));

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
      return jwtUtil.generarToken(username);
    } catch (Exception e) {
      throw new BadCredentialsException("Refresh token inválido o expirado");
    }
  }

  public ResultadoSP obtenerPerfilDesdeToken() {
    ResultadoSP resultado = usuarioService.obtenerPerfil();
    if (!resultado.esExitoso()) {
      return resultado;
    }

    Usuario usuario = (Usuario) resultado.getData();
    usuario.setPassword(null);
    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));

    return new ResultadoSP(1, "Perfil obtenido correctamente", usuario);
  }
}
