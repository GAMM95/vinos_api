package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.UsuarioView;
import com.gamm.vinos_api.repository.UsuarioAuthRepository;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioAuthRepository usuarioAuthRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public ResultadoSP registrarUsuario(Usuario usuario) {
    if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser null");
    if (usuario.getUsername() == null || usuario.getUsername().isBlank())
      return new ResultadoSP(0, "Username obligatorio");
    if (usuario.getPassword() == null || usuario.getPassword().isBlank())
      return new ResultadoSP(0, "Password obligatorio");
    if (usuario.getPersona() == null)
      return new ResultadoSP(0, "Datos de persona obligatorios");

    try {
      // Encriptar password
      usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

      // Registrar en BD
      return usuarioAuthRepository.registrarUsuario(usuario);

    } catch (Exception e) {
      // Manejo genérico de errores
      return new ResultadoSP(0, "Error al registrar usuario: " + e.getMessage());
    }
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
  public List<UsuarioView> listarUsuarios() {
    return usuarioRepository.listarUsuarios();
  }
}
