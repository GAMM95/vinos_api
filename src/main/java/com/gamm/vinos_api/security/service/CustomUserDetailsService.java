package com.gamm.vinos_api.security.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UsuarioService usuarioService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ResultadoSP resultado = usuarioService.login(username);
    Usuario usuario = (Usuario) resultado.getData(); // data del SP
    if (usuario == null) throw new UsernameNotFoundException(resultado.getMensaje());

    return User
        .withUsername(usuario.getUsername())
        .password(usuario.getPassword())
        .roles(usuario.getRol().name())
        .build();
  }
}
