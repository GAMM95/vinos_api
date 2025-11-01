package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
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
      return usuarioRepository.registrarUsuario(usuario);

    } catch (Exception e) {
      // Manejo genérico de errores
      return new ResultadoSP(0, "Error al registrar usuario: " + e.getMessage());
    }
  }

  @Override
  public Usuario obtenerPorUsername(String username) {
    if (username == null || username.isBlank()) return null;
    try {
      return usuarioRepository.obtenerPorUsername(username);
    } catch (Exception e) {
      return null;
    }
  }
}
