package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.UsuarioView;
import com.gamm.vinos_api.repository.UsuarioAuthRepository;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.service.FotoService;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
  public ResultadoSP obtenerPerfil(String username) {
    return usuarioAuthRepository.obtenerDatosPerfil(username);
  }

  @Override
  public ResultadoSP actualizarUsuario(Usuario usuario) {
     return usuarioRepository.actualizarUsuario(usuario);
  }

  @Override
  public ResultadoSP resetearPassword(Integer idUsuario, String nuevaPassword) {
    String encriptada = passwordEncoder.encode(nuevaPassword); // Encriptar nueva contraseña
    return usuarioAuthRepository.resetearPassword(idUsuario, encriptada);
  }

  @Override
  public ResultadoSP cambiarPassword(Integer idUsuario, String actual, String nueva) {
    // Primero obtenemos el usuario

    Usuario u = new Usuario();
    u.getIdUsuario();

    // Validar contraseña actual
    if (!passwordEncoder.matches(actual, u.getPassword()))
      return new ResultadoSP(0, "La contraseña actual es incorrecta");

    // Encriptar nueva
    String nuevaEnc = passwordEncoder.encode(nueva);

    // Enviar al SP
    return usuarioAuthRepository.cambiarPassword(idUsuario, u.getPassword(), nuevaEnc);
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
      // Asegurar mensaje claro si la SP devuelve null o vacío
      String mensaje = (resultado.getMensaje() != null && !resultado.getMensaje().isEmpty())
          ? resultado.getMensaje()
          : "Foto actualizada correctamente";
      return new ResultadoSP(1, mensaje, ruta); // siempre marcar como exitoso si se guardó
    } catch (Exception e) {
      return new ResultadoSP(0, "Error al guardar la foto: " + e.getMessage());
    }
  }
}
