//package com.gamm.vinos_api.security.service;
//
//import com.gamm.vinos_api.domain.model.Usuario;
//import com.gamm.vinos_api.security.jwt.JwtUtil;
//import com.gamm.vinos_api.service.UsuarioService;
//import com.gamm.vinos_api.utils.ResultadoSP;
//import lombok.RequiredArgsConstructor;
////import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//  //  private final AuthenticationManager am;
//  private final JwtUtil jwtUtil;
//  private final UsuarioService usuarioService;
//  private final PasswordEncoder passwordEncoder;
//
//  public ResultadoSP login(String username, String password) {
//    ResultadoSP resultado = usuarioService.login(username);
//    Usuario usuario = (Usuario) resultado.getData();
//
//    if (!resultado.esExitoso() || usuario == null)
//      return new ResultadoSP(0, resultado.getMensaje(), null);
//
//    if (!passwordEncoder.matches(password, usuario.getPassword()))
//      return new ResultadoSP(0, "Credenciales incorrectas.", null);
//
//    usuario.setPassword(null);
//    Map<String, Object> data = Map.of(
//        "usuario", usuario,
//        "accessToken", jwtUtil.generarToken(usuario.getUsername()),
//        "refreshToken", jwtUtil.generarRefreshToken(usuario.getUsername())
//    );
//    return new ResultadoSP(1, resultado.getMensaje(), data);
//  }
//
//  public String refreshToken(String refreshToken) {
//    try {
//      String username = jwtUtil.obtenerUsername(refreshToken);
//      return jwtUtil.generarToken(username); // Nuevo access token
//    } catch (Exception e) {
//      throw new BadCredentialsException("Refresh token inválido o expirado");
//    }
//  }
//
//  public ResultadoSP obtenerPerfilDesdeToken(String bearerToken) {
//    try {
//      // Quitar "Bearer "
//      String token = bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : bearerToken;
//
//      // Obtener username del token
//      String username = jwtUtil.obtenerUsername(token);
//
//      // Consultar los datos completos del usuario
//      ResultadoSP resultado = usuarioService.obtenerPerfil(username);
//
//      if (!resultado.esExitoso()) {
//        return new ResultadoSP(0, resultado.getMensaje(), null);
//      }
//
//      // Evitar enviar password
//      Usuario usuario = (Usuario) resultado.getData();
//      usuario.setPassword(null);
//
//      return new ResultadoSP(1, "Perfil obtenido correctamente", usuario);
//    } catch (Exception e) {
//      return new ResultadoSP(0, "No se pudo obtener el perfil: " + e.getMessage(), null);
//    }
//  }
//
//}
////
//
//package com.gamm.vinos_api.security.service;
//
//import com.gamm.vinos_api.domain.model.Usuario;
//import com.gamm.vinos_api.security.jwt.JwtUtil;
//import com.gamm.vinos_api.service.UsuarioService;
//import com.gamm.vinos_api.utils.ResultadoSP;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//  private final JwtUtil jwtUtil;
//  private final UsuarioService usuarioService;
//  private final PasswordEncoder passwordEncoder;
//
//  // URL base donde se sirven las fotos (configurable en application.properties)
//  @Value("${fotosUsuarios.url:/FotosUsuarios/}")
//  private String fotosUsuariosUrl;
//
//  public ResultadoSP login(String username, String password) {
//    ResultadoSP resultado = usuarioService.login(username);
//    Usuario usuario = (Usuario) resultado.getData();
//
//    if (!resultado.esExitoso() || usuario == null)
//      return new ResultadoSP(0, resultado.getMensaje(), null);
//
//    if (!passwordEncoder.matches(password, usuario.getPassword()))
//      return new ResultadoSP(0, "Credenciales incorrectas.", null);
//
//    usuario.setPassword(null);
//
//    // Ajustar rutaFoto al login también
//    if (usuario.getRutaFoto() == null || usuario.getRutaFoto().trim().isEmpty()) {
//      usuario.setRutaFoto(null);
//    } else if (!usuario.getRutaFoto().startsWith("http")) {
//      // Si es relativa, concatenar
//      usuario.setRutaFoto(fotosUsuariosUrl + usuario.getRutaFoto());
//    }
//    Map<String, Object> data = Map.of(
//        "usuario", usuario,
//        "accessToken", jwtUtil.generarToken(usuario.getUsername()),
//        "refreshToken", jwtUtil.generarRefreshToken(usuario.getUsername())
//    );
//    return new ResultadoSP(1, resultado.getMensaje(), data);
//  }
//
//  public String refreshToken(String refreshToken) {
//    try {
//      String username = jwtUtil.obtenerUsername(refreshToken);
//      return jwtUtil.generarToken(username); // Nuevo access token
//    } catch (Exception e) {
//      throw new BadCredentialsException("Refresh token inválido o expirado");
//    }
//  }
//
//  public ResultadoSP obtenerPerfilDesdeToken(String bearerToken) {
//    try {
//      // Quitar "Bearer "
//      String token = bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : bearerToken;
//
//      // Obtener username del token
//      String username = jwtUtil.obtenerUsername(token);
//
//      // Consultar los datos completos del usuario
//      ResultadoSP resultado = usuarioService.obtenerPerfil(username);
//
//      if (!resultado.esExitoso()) {
//        return new ResultadoSP(0, resultado.getMensaje(), null);
//      }
//
//      Usuario usuario = (Usuario) resultado.getData();
//      usuario.setPassword(null);
//
//      // Ajustar rutaFoto antes de enviarlo
//      if (usuario.getRutaFoto() == null || usuario.getRutaFoto().trim().isEmpty()) {
//        usuario.setRutaFoto(null);
//      } else if (!usuario.getRutaFoto().startsWith("http")) {
//        // Si es relativa, concatenar
//        usuario.setRutaFoto(fotosUsuariosUrl + usuario.getRutaFoto());
//      }
//
//      return new ResultadoSP(1, "Perfil obtenido correctamente", usuario);
//    } catch (Exception e) {
//      return new ResultadoSP(0, "No se pudo obtener el perfil: " + e.getMessage(), null);
//    }
//  }
//}





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

  public ResultadoSP obtenerPerfilDesdeToken(String bearerToken) {
    try {
      String token = bearerToken.startsWith("Bearer ")
          ? bearerToken.substring(7)
          : bearerToken;

      String username = jwtUtil.obtenerUsername(token);

      ResultadoSP resultado = usuarioService.obtenerPerfil(username);

      if (!resultado.esExitoso()) {
        return new ResultadoSP(0, resultado.getMensaje(), null);
      }

      Usuario usuario = (Usuario) resultado.getData();
      usuario.setPassword(null);

      usuario.setRutaFoto(buildFotoUrl(usuario.getRutaFoto()));

      return new ResultadoSP(1, "Perfil obtenido correctamente", usuario);

    } catch (Exception e) {
      return new ResultadoSP(0, "No se pudo obtener el perfil: " + e.getMessage(), null);
    }
  }
}
