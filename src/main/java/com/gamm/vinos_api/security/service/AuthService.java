package com.gamm.vinos_api.security.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.security.jwt.JwtUtil;
import com.gamm.vinos_api.service.EmailService;
import com.gamm.vinos_api.service.PasswordResetTokenService;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtUtil jwtUtil;
  private final UsuarioService usuarioService;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;
  private final PasswordResetTokenService passwordResetTokenService;

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${fotosUsuarios.url}")
  private String fotosUsuariosUrl;

  /* Construir url para la foto */
  private String buildFotoUrl(String rutaFoto) {
    if (rutaFoto == null || rutaFoto.trim().isEmpty()) return null;
    rutaFoto = rutaFoto.trim();
    if (rutaFoto.startsWith("http://") || rutaFoto.startsWith("https://")) return rutaFoto;
    if (rutaFoto.startsWith("/FotosUsuarios/")) {
      rutaFoto = rutaFoto.substring("/FotosUsuarios/".length());
    }
    return fotosUsuariosUrl + "/FotosUsuarios/" + rutaFoto;
  }

  /* Inicio de sesion */
  public ResultadoSP login(String username, String password) {

    ResultadoSP resultado = usuarioService.login(username);
    Usuario usuario = (Usuario) resultado.getData();

    if (!resultado.esExitoso() || usuario == null) {
      System.out.println("Usuario no encontrado para username: " + username);
      return new ResultadoSP(0, resultado.getMensaje(), null);
    }

    // Normalizar password
    String passwordLimpia = password.trim();

    boolean match = passwordEncoder.matches(passwordLimpia, usuario.getPassword());
    System.out.println("Password match: " + match);

    if (!match) {
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

  // Refrescar token
  public String refreshToken(String refreshToken) {
    try {
      String username = jwtUtil.obtenerUsername(refreshToken);
      return jwtUtil.generarToken(username);
    } catch (Exception e) {
      throw new BadCredentialsException("Refresh token inválido o expirado");
    }
  }

  /* Obtener datos de perfil */
  public ResultadoSP obtenerPerfilDesdeToken() {
    ResultadoSP resultado = usuarioService.obtenerPerfil();
    if (!resultado.esExitoso()) return resultado;

    Usuario usuario = (Usuario) resultado.getData();
    usuario.setPassword(null);
    usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));

    return new ResultadoSP(1, "Perfil obtenido correctamente", usuario);
  }

  /* Generar token de recuperacion enviando al email */
  public ResultadoSP generarTokenRecuperacion(String email) {
    Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
    if (usuario == null) return new ResultadoSP(0, "Email no registrado");

    // Invalidar tokens previos
    passwordResetTokenService.invalidarPorUsuario(usuario.getIdUsuario());

    // Generar token aleatorio seguro
    String tokenPlano = UUID.randomUUID().toString();

    // Guardar hash en BD
    ResultadoSP rsp = passwordResetTokenService.crearToken(usuario.getIdUsuario(), tokenPlano);
    if (!rsp.esExitoso()) return rsp;

    // Enviar email con token plano
    try {
      emailService.enviarRecuperacion(usuario.getPersona().getEmail(), tokenPlano);
    } catch (Exception e) {
      return new ResultadoSP(0, "No se pudo enviar el email: " + e.getMessage());
    }

    return new ResultadoSP(1, "Se envió un email con instrucciones", null);
  }

  /* Resetear password con token */
  public ResultadoSP resetearPasswordConToken(String tokenPlano, String nuevaPassword) {
    // Generar hash del token
    String tokenHash = DigestUtils.sha256Hex(tokenPlano);

    // Validar token en BD
    ResultadoSP validacion = passwordResetTokenService.validarToken(tokenPlano);

    if (!validacion.esExitoso()) {
      System.out.println("Token inválido o expirado.");
      return new ResultadoSP(0, "Token inválido o expirado");
    }

    Integer idUsuario = (Integer) validacion.getData();
    System.out.println("ID usuario obtenido desde token: " + idUsuario);

    if (idUsuario == null) {
      System.out.println("Error: idUsuario es null después de validar token.");
      return new ResultadoSP(0, "No se pudo obtener el usuario desde el token.");
    }

    // Marcar token como usado
    ResultadoSP marcado = passwordResetTokenService.marcarComoUsado(tokenPlano);
    System.out.println("Resultado marcar token como usado: " + marcado);

    if (!marcado.esExitoso()) {
      System.out.println("Error: no se pudo marcar el token como usado.");
      return new ResultadoSP(0, "No se pudo marcar el token como usado");
    }

    // 4️ Actualizar contraseña
    String passwordHash = passwordEncoder.encode(nuevaPassword.trim());

    ResultadoSP cambio = usuarioService.resetearPasswordToken(idUsuario, passwordHash);

    if (!cambio.esExitoso()) {
      System.out.println("Error al actualizar la contraseña en la BD: " + cambio.getMensaje());
      return cambio;
    }

    return new ResultadoSP(1, "Contraseña actualizada correctamente", null);
  }

}