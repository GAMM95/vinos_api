package com.gamm.vinos_api.security.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.security.jwt.JwtUtil;
import com.gamm.vinos_api.service.EmailService;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtUtil jwtUtil;
  private final UsuarioService usuarioService;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;

  @Value("${fotosUsuarios.url}")
  private String fotosUsuariosUrl;

  private String buildFotoUrl(String rutaFoto) {
    if (rutaFoto == null || rutaFoto.trim().isEmpty()) return null;
    rutaFoto = rutaFoto.trim();
    if (rutaFoto.startsWith("http://") || rutaFoto.startsWith("https://")) return rutaFoto;
    if (rutaFoto.startsWith("/FotosUsuarios/")) rutaFoto = rutaFoto.substring("/FotosUsuarios/".length());
    return fotosUsuariosUrl + "/FotosUsuarios/" + rutaFoto;
  }

  public ResultadoSP login(String username, String password) {
    ResultadoSP resultado = usuarioService.login(username);
    Usuario usuario = (Usuario) resultado.getData();
    if (!resultado.esExitoso() || usuario == null) return new ResultadoSP(0, resultado.getMensaje(), null);
    if (!passwordEncoder.matches(password, usuario.getPassword()))
      return new ResultadoSP(0, "Credenciales incorrectas.", null);

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
    if (!resultado.esExitoso()) return resultado;

    Usuario usuario = (Usuario) resultado.getData();
    usuario.setPassword(null);
    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));

    return new ResultadoSP(1, "Perfil obtenido correctamente", usuario);
  }

  // Recuperación de contraseña
  public ResultadoSP generarTokenRecuperacion(String email) {
    Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
    if (usuario == null) return new ResultadoSP(0, "Email no registrado");

    // Generar token usando JwtUtil
    String token = jwtUtil.generarToken(usuario.getPersona().getEmail());

    // Envío de email
    try {
      emailService.enviarRecuperacion(usuario.getPersona().getEmail(), token);
    } catch (Exception e) {
      return new ResultadoSP(0, "No se pudo enviar el email: " + e.getMessage());
    }

    return new ResultadoSP(1, "Se envió un email con instrucciones", token);
  }


//  public ResultadoSP resetearPasswordConToken(String token, String nuevaPassword) {
//    Integer idUsuario;
//    try {
//      byte[] keyBytes = "claveSuperSecreta".getBytes(StandardCharsets.UTF_8);
//      Claims claims = Jwts.parserBuilder()
//          .setSigningKey(Keys.hmacShaKeyFor(keyBytes)) // reemplaza setSigningKey(String)
//          .build()
//          .parseClaimsJws(token)
//          .getBody();
//
//      idUsuario = Integer.parseInt(claims.getSubject());
//    } catch (ExpiredJwtException e) {
//      return new ResultadoSP(0, "Token expirado");
//    } catch (Exception e) {
//      return new ResultadoSP(0, "Token inválido");
//    }
//
//    Usuario usuario = usuarioService.obtenerPorId(idUsuario);
//    if (usuario == null) return new ResultadoSP(0, "Usuario no encontrado");
//
//    usuario.setPassword(passwordEncoder.encode(nuevaPassword));
//    return usuarioService.resetearPassword(idUsuario, usuario.getPassword());
//  }
public ResultadoSP resetearPasswordConToken(String token, String nuevaPassword) {
  String emailUsuario;
  try {
    // Usa la misma clave que al generar el token
    byte[] keyBytes = "claveSuperSecreta".getBytes(StandardCharsets.UTF_8);
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
        .build()
        .parseClaimsJws(token)
        .getBody();

    // El subject ahora es el email
    emailUsuario = claims.getSubject();
  } catch (ExpiredJwtException e) {
    return new ResultadoSP(0, "Token expirado");
  } catch (Exception e) {
    return new ResultadoSP(0, "Token inválido");
  }

  // Buscar al usuario por email en lugar de ID
  Usuario usuario = usuarioService.obtenerUsuarioPorEmail(emailUsuario);
  if (usuario == null) return new ResultadoSP(0, "Usuario no encontrado");

  // Actualizar la contraseña
  usuario.setPassword(passwordEncoder.encode(nuevaPassword));
  return usuarioService.resetearPassword(usuario.getIdUsuario(), usuario.getPassword());
}

}
