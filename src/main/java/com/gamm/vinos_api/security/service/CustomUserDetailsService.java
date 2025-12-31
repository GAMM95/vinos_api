package com.gamm.vinos_api.security.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.security.UsuarioPrincipal;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UsuarioService usuarioService;

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {

    ResultadoSP resultado = usuarioService.login(username);
    Usuario usuario = (Usuario) resultado.getData();

    if (usuario == null) {
      throw new UsernameNotFoundException(resultado.getMensaje());
    }

    return new UsuarioPrincipal(
        usuario.getIdUsuario(),
        usuario.getUsername(),
        usuario.getPassword(),
        List.of(
            new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
        )
    );
  }
}
