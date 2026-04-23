package com.gamm.vinos_api.security.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.exception.business.BusinessException;
import com.gamm.vinos_api.security.jwt.JwtUtil;
import com.gamm.vinos_api.service.EmailService;
import com.gamm.vinos_api.service.PasswordResetTokenService;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtUtil jwtUtil;
  private final UsuarioService usuarioService;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;
  private final PasswordResetTokenService passwordResetTokenService;

  @Value("${fotosUsuarios.url}")
  private String fotosUsuariosUrl;

  // ─── Login ────────────────────────────────────────────────────────────────
  public Map<String, Object> login(String username, String password) {
    ResultadoSP resultado = usuarioService.login(username);
    ResponseVO.validar(resultado); // → SP Tipo 2: "Usuario no existe." / "Usuario inactivo."

    Usuario usuario = (Usuario) resultado.getData();

    if (!passwordEncoder.matches(password.trim(), usuario.getPassword())) {
      throw new BusinessException("Credenciales incorrectas.");
    }

    log.info("Login exitoso para username: {}", username);
    usuario.setPassword(null);
    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));

    return Map.of(
        "usuario", usuario,
        "accessToken", jwtUtil.generarToken(usuario.getUsername()),
        "refreshToken", jwtUtil.generarRefreshToken(usuario.getUsername())
    );
  }

  // ─── Refresh token ────────────────────────────────────────────────────────

  public String refreshToken(String refreshToken) {
    // validarRefreshToken lanza InvalidRefreshTokenException si no es de tipo refresh
    // extraerClaims lanza TokenExpiradoException / TokenInvalidoException si está vencido o corrupto
    jwtUtil.validarRefreshToken(refreshToken);
    String username = jwtUtil.obtenerUsername(refreshToken);
    log.info("Access token renovado para username: {}", username);
    return jwtUtil.generarToken(username);
  }

  // ─── Perfil ───────────────────────────────────────────────────────────────

  public Usuario obtenerPerfil() {
    ResultadoSP resultado = usuarioService.obtenerPerfil();
    ResponseVO.validar(resultado); // si falla, BusinessException con mensaje del SP

    Usuario usuario = (Usuario) resultado.getData();
    usuario.setPassword(null);
    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));
    return usuario;
  }

  // ─── Recuperación de contraseña ───────────────────────────────────────────

  public void generarTokenRecuperacion(String email) {
    Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
    if (usuario == null) {
      throw new BusinessException("Email no registrado.");
    }

    passwordResetTokenService.invalidarPorUsuario(usuario.getIdUsuario());

    String tokenPlano = UUID.randomUUID().toString();
    ResultadoSP rsp = passwordResetTokenService.crearToken(usuario.getIdUsuario(), tokenPlano);
    ResponseVO.validar(rsp); // si el SP falla al crear el token

    try {
      emailService.enviarRecuperacion(usuario.getPersona().getEmail(), tokenPlano);
      log.info("Email de recuperación enviado a: {}", email);
    } catch (Exception e) {
      log.error("Error enviando email de recuperación a {}: {}", email, e.getMessage());
      throw new BusinessException("No se pudo enviar el email de recuperación.");
    }
  }

  // ─── Resetear contraseña con token ────────────────────────────────────────

  public void resetearPasswordConToken(String tokenPlano, String nuevaPassword) {
    ResultadoSP validacion = passwordResetTokenService.validarToken(tokenPlano);
    ResponseVO.validar(validacion); // → "Token inválido o expirado"

    Integer idUsuario = (Integer) validacion.getData();
    if (idUsuario == null) {
      log.error("Token válido pero sin idUsuario asociado");
      throw new BusinessException("No se pudo obtener el usuario desde el token.");
    }

    ResultadoSP marcado = passwordResetTokenService.marcarComoUsado(tokenPlano);
    ResponseVO.validar(marcado); // si falla al marcar

    String passwordHash = passwordEncoder.encode(nuevaPassword.trim());
    ResultadoSP cambio = usuarioService.resetearPasswordToken(idUsuario, passwordHash);
    ResponseVO.validar(cambio); // → mensaje del SP si falla

    log.info("Contraseña reseteada correctamente para idUsuario: {}", idUsuario);
  }

  // ─── Helper ───────────────────────────────────────────────────────────────
  private String buildFotoUrl(String rutaFoto) {
    if (rutaFoto == null || rutaFoto.trim().isEmpty()) return null;
    rutaFoto = rutaFoto.trim();
    if (rutaFoto.startsWith("http://") || rutaFoto.startsWith("https://")) return rutaFoto;
    if (rutaFoto.startsWith("/FotosUsuarios/")) {
      rutaFoto = rutaFoto.substring("/FotosUsuarios/".length());
    }
    return fotosUsuariosUrl + "/FotosUsuarios/" + rutaFoto;
  }
}