package com.gamm.vinos_api.security.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.security.UsuarioPrincipal;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.util.ResultadoSP;
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
    if (resultado == null || resultado.getData() == null) {
      throw new UsernameNotFoundException(
          resultado != null ? resultado.getMensaje() : "Usuario no encontrado"
      );
    }

    Usuario usuario = (Usuario) resultado.getData();

    Integer idSucursal = usuario.getSucursal() != null
        ? usuario.getSucursal().getIdSucursal()
        : null;

    String rol = usuario.getRol() != null
        ? usuario.getRol().name()
        : "USER";

    String nombreCompleto = usuario.getPersona() != null
        ? usuario.getPersona().getNombres() + " " + usuario.getPersona().getApellidoPaterno()
        : usuario.getUsername();

    String sucursal = usuario.getSucursal() != null
        ? usuario.getSucursal().getNombre()
        : null;

    return new UsuarioPrincipal(
        usuario.getIdUsuario(),
        usuario.getUsername(),
        nombreCompleto,
        usuario.getPassword(),
        idSucursal,
        sucursal,
        rol,
        List.of(new SimpleGrantedAuthority("ROLE_" + rol))
    );
  }
}
