//package com.gamm.vinos_api.security.service;
//
//import com.gamm.vinos_api.domain.model.Usuario;
//import com.gamm.vinos_api.dto.response.ResponseVO;
//import com.gamm.vinos_api.exception.business.BusinessException;
//import com.gamm.vinos_api.security.jwt.JwtUtil;
//import com.gamm.vinos_api.service.EmailService;
//import com.gamm.vinos_api.service.PasswordResetTokenService;
//import com.gamm.vinos_api.service.UsuarioService;
//import com.gamm.vinos_api.util.ResultadoSP;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//  private final JwtUtil jwtUtil;
//  private final UsuarioService usuarioService;
//  private final PasswordEncoder passwordEncoder;
//  private final EmailService emailService;
//  private final PasswordResetTokenService passwordResetTokenService;
//
//  @Value("${fotosUsuarios.url}")
//  private String fotosUsuariosUrl;
//
//  /* Construir url para la foto */
//  private String buildFotoUrl(String rutaFoto) {
//    if (rutaFoto == null || rutaFoto.trim().isEmpty()) return null;
//    rutaFoto = rutaFoto.trim();
//    if (rutaFoto.startsWith("http://") || rutaFoto.startsWith("https://")) return rutaFoto;
//    if (rutaFoto.startsWith("/FotosUsuarios/")) {
//      rutaFoto = rutaFoto.substring("/FotosUsuarios/".length());
//    }
//    return fotosUsuariosUrl + "/FotosUsuarios/" + rutaFoto;
//  }
//
//  /* Inicio de sesion */
////  public ResultadoSP login(String username, String password) {
////    ResultadoSP resultado = usuarioService.login(username);
////    Usuario usuario = (Usuario) resultado.getData();
////
////    if (!resultado.esExitoso() || usuario == null) {
////      log.warn("Intento de login fallido para username: {}", username);
////      return new ResultadoSP(0, resultado.getMensaje(), null);
////    }
////
////    if (!passwordEncoder.matches(password.trim(), usuario.getPassword())) {
////      log.warn("Contraseña incorrecta para username: {}", username);
////      return new ResultadoSP(0, "Credenciales incorrectas.", null);
////    }
////
////    log.info("Login exitoso para username: {}", username);
////    usuario.setPassword(null);
////    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));
////
////    Map<String, Object> data = Map.of(
////        "usuario", usuario,
////        "accessToken", jwtUtil.generarToken(usuario.getUsername()),
////        "refreshToken", jwtUtil.generarRefreshToken(usuario.getUsername())
////    );
////
////    return new ResultadoSP(1, resultado.getMensaje(), data);
////  }
//
//  // El service ya no retorna ResultadoSP — retorna el dato directamente
//  public Map<String, Object> login(String username, String password) {
//    ResultadoSP resultado = usuarioService.login(username);
//    ResponseVO.validar(resultado); // → SP Tipo 2: "Usuario no existe." / "Usuario inactivo."
//
//    Usuario usuario = (Usuario) resultado.getData();
//
//    if (!passwordEncoder.matches(password.trim(), usuario.getPassword())) {
//      throw new BusinessException("Credenciales incorrectas.");
//    }
//
//    log.info("Login exitoso para username: {}", username);
//    usuario.setPassword(null);
//    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));
//
//    return Map.of(
//        "usuario", usuario,
//        "accessToken", jwtUtil.generarToken(usuario.getUsername()),
//        "refreshToken", jwtUtil.generarRefreshToken(usuario.getUsername())
//    );
//  }
//
//  // Refrescar token
//  public String refreshToken(String refreshToken) {
//    // validarRefreshToken lanza InvalidRefreshTokenException si no es de tipo refresh
//    // extraerClaims lanza TokenExpiradoException / TokenInvalidoException si está vencido o corrupto
//    jwtUtil.validarRefreshToken(refreshToken);
//    String username = jwtUtil.obtenerUsername(refreshToken);
//    log.info("Access token renovado para username: {}", username);
//    return jwtUtil.generarToken(username);
//  }
//
//  /* Obtener datos de perfil */
////  public ResultadoSP obtenerPerfilDesdeToken() {
////    ResultadoSP resultado = usuarioService.obtenerPerfil();
////    if (!resultado.esExitoso()) return resultado;
////
////    Usuario usuario = (Usuario) resultado.getData();
////    usuario.setPassword(null);
////    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));
////
////    return new ResultadoSP(1, "Perfil obtenido correctamente", usuario);
////  }
//
//  // Perfil
//  public Usuario obtenerPerfil() {
//    ResultadoSP resultado = usuarioService.obtenerPerfil();
//    ResponseVO.validar(resultado); // si falla, BusinessException con mensaje del SP
//
//    Usuario usuario = (Usuario) resultado.getData();
//    usuario.setPassword(null);
//    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));
//    return usuario;
//  }
//
//  /* Generar token de recuperacion enviando al email */
////  public ResultadoSP generarTokenRecuperacion(String email) {
////    Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
////    if (usuario == null) return new ResultadoSP(0, "Email no registrado");
////
////    passwordResetTokenService.invalidarPorUsuario(usuario.getIdUsuario());
////
////    String tokenPlano = UUID.randomUUID().toString();
////    ResultadoSP rsp = passwordResetTokenService.crearToken(usuario.getIdUsuario(), tokenPlano);
////    if (!rsp.esExitoso()) return rsp;
////
////    try {
////      emailService.enviarRecuperacion(usuario.getPersona().getEmail(), tokenPlano);
////    } catch (Exception e) {
////      log.error("Error enviando email de recuperación a {}: {}", email, e.getMessage());
////      return new ResultadoSP(0, "No se pudo enviar el email de recuperación.");
////    }
////
////    return new ResultadoSP(1, "Se envió un email con instrucciones", null);
////  }
//
//  // Recuperar contraseña
//  public void generarTokenRecuperacion(String email) {
//    Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
//    if (usuario == null) {
//      throw new BusinessException("Email no registrado.");
//    }
//
//    passwordResetTokenService.invalidarPorUsuario(usuario.getIdUsuario());
//
//    String tokenPlano = UUID.randomUUID().toString();
//    ResultadoSP rsp = passwordResetTokenService.crearToken(usuario.getIdUsuario(), tokenPlano);
//    ResponseVO.validar(rsp); // si el SP falla al crear el token
//
//    try {
//      emailService.enviarRecuperacion(usuario.getPersona().getEmail(), tokenPlano);
//      log.info("Email de recuperación enviado a: {}", email);
//    } catch (Exception e) {
//      log.error("Error enviando email de recuperación a {}: {}", email, e.getMessage());
//      throw new BusinessException("No se pudo enviar el email de recuperación.");
//    }
//  }
//
//  /* Resetear password con token */
////  public ResultadoSP resetearPasswordConToken(String tokenPlano, String nuevaPassword) {
////    ResultadoSP validacion = passwordResetTokenService.validarToken(tokenPlano);
////
////    if (!validacion.esExitoso()) {
////      log.warn("Intento de reset con token inválido o expirado");
////      return new ResultadoSP(0, "Token inválido o expirado");
////    }
////
////    Integer idUsuario = (Integer) validacion.getData();
////    if (idUsuario == null) {
////      log.error("Token válido pero sin idUsuario asociado");
////      return new ResultadoSP(0, "No se pudo obtener el usuario desde el token.");
////    }
////
////    ResultadoSP marcado = passwordResetTokenService.marcarComoUsado(tokenPlano);
////    if (!marcado.esExitoso()) {
////      log.error("No se pudo marcar el token como usado para idUsuario: {}", idUsuario);
////      return new ResultadoSP(0, "No se pudo procesar la solicitud.");
////    }
////
////    String passwordHash = passwordEncoder.encode(nuevaPassword.trim());
////    ResultadoSP cambio = usuarioService.resetearPasswordToken(idUsuario, passwordHash);
////
////    if (!cambio.esExitoso()) {
////      log.error("Error al actualizar contraseña para idUsuario {}: {}", idUsuario, cambio.getMensaje());
////      return cambio;
////    }
////
////    log.info("Contraseña reseteada correctamente para idUsuario: {}", idUsuario);
////    return new ResultadoSP(1, "Contraseña actualizada correctamente", null);
////  }
//
//  public void resetearPasswordConToken(String tokenPlano, String nuevaPassword) {
//    ResultadoSP validacion = passwordResetTokenService.validarToken(tokenPlano);
//    ResponseVO.validar(validacion); // → "Token inválido o expirado"
//
//    Integer idUsuario = (Integer) validacion.getData();
//    if (idUsuario == null) {
//      log.error("Token válido pero sin idUsuario asociado");
//      throw new BusinessException("No se pudo obtener el usuario desde el token.");
//    }
//
//    ResultadoSP marcado = passwordResetTokenService.marcarComoUsado(tokenPlano);
//    ResponseVO.validar(marcado); // si falla al marcar
//
//    String passwordHash = passwordEncoder.encode(nuevaPassword.trim());
//    ResultadoSP cambio = usuarioService.resetearPasswordToken(idUsuario, passwordHash);
//    ResponseVO.validar(cambio); // → mensaje del SP si falla
//
//    log.info("Contraseña reseteada correctamente para idUsuario: {}", idUsuario);
//  }
//
//  // Helper
//  private String buildFotoUrl(String rutaFoto) {
//    if (rutaFoto == null || rutaFoto.trim().isEmpty()) return null;
//    rutaFoto = rutaFoto.trim();
//    if (rutaFoto.startsWith("http://") || rutaFoto.startsWith("https://")) return rutaFoto;
//    if (rutaFoto.startsWith("/FotosUsuarios/")) {
//      rutaFoto = rutaFoto.substring("/FotosUsuarios/".length());
//    }
//    return fotosUsuariosUrl + "/FotosUsuarios/" + rutaFoto;
//  }
//}


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