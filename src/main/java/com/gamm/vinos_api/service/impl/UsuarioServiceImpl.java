package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.UsuarioView;
import com.gamm.vinos_api.dto.UsuarioEmail;
import com.gamm.vinos_api.repository.UsuarioAuthRepository;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.FotoService;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioAuthRepository usuarioAuthRepository;
  private final PasswordEncoder passwordEncoder;
  private final FotoService fotoService;

  @Override
  public ResultadoSP registrarUsuario(Usuario usuario) {
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encriptar contraseña
    return usuarioAuthRepository.registrarUsuario(usuario);
  }

  @Override
  public ResultadoSP login(String username) {
    return usuarioAuthRepository.login(username);
  }

  @Override
  public ResultadoSP inactivarUsuario(Integer idUsuario) {
    return usuarioRepository.inactivarUsuario(idUsuario);
  }

  @Override
  public ResultadoSP activarUsuario(Integer idUsuario) {
    return usuarioRepository.activarUsuario(idUsuario);
  }

  @Override
  public ResultadoSP filtrarUsuario(String terminoBusqueda) {
    return usuarioRepository.filtrarUsuario(terminoBusqueda);
  }

  @Override
  public ResultadoSP obtenerPerfil() {
    String username = SecurityUtils.getUsername();
    return usuarioAuthRepository.obtenerDatosPerfil(username);
  }

  @Override
  public ResultadoSP actualizarUsuario(Usuario usuario) {
    ResultadoSP r = usuarioRepository.actualizarUsuario(usuario);
    if (r.esExitoso()) {
      Usuario actualizado = usuarioRepository.obtenerPorId(usuario.getIdUsuario());
      r.setData(actualizado);
    }
    return r;
  }

  @Override
  public ResultadoSP resetearPassword(Integer idUsuario, String nuevaPassword) {
    String encriptada = passwordEncoder.encode(nuevaPassword); // Encriptar nueva contraseña
    return usuarioAuthRepository.resetearPassword(idUsuario, encriptada);
  }

  @Override
  public ResultadoSP cambiarPassword(String actual, String nueva) {
    Integer idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) {
      return new ResultadoSP(0, "Usuario no autenticado");
    }
    Usuario u = usuarioRepository.obtenerUsuarioConPassword(idUsuario);
    if (u == null) {
      return new ResultadoSP(0, "Usuario no encontrado");
    }
//    System.out.println("PASSWORD BD = " + u.getPassword());
//    System.out.println("ACTUAL = " + actual);

    if (!passwordEncoder.matches(actual, u.getPassword())) {
      return new ResultadoSP(0, "La contraseña actual es incorrecta");
    }

    String nuevaEncriptada = passwordEncoder.encode(nueva);
    return usuarioAuthRepository.cambiarPassword(
        idUsuario,
        u.getPassword(),
        nuevaEncriptada
    );
  }

  @Override
  public List<UsuarioView> listarUsuarios() {
    return usuarioRepository.listarUsuarios();
  }

  @Override
  public ResultadoSP actualizarFoto(Integer idUsuario, MultipartFile foto) {
    try {
      String ruta = fotoService.guardarFoto(idUsuario, foto);

      ResultadoSP resultado = usuarioRepository.actualizarFoto(idUsuario, ruta);
      if (!resultado.esExitoso()) {
        return resultado;
      }
      resultado.setData(ruta);
      return resultado;

    } catch (Exception e) {
      return new ResultadoSP(0, "Error al guardar la foto: " + e.getMessage());
    }
  }

  @Override
  public ResultadoSP verificarUsername(String username, Integer idUsuario) {
    return usuarioRepository.verificarUsername(username, idUsuario);
  }

  @Override
  public Usuario obtenerPorId(Integer idUsuario) {
    return usuarioRepository.obtenerPorId(idUsuario);
  }

  @Override
  public Usuario obtenerUsuarioPorEmail(String email) {
    return usuarioRepository.obtenerUsuarioPorEmail(email);
  }
}
