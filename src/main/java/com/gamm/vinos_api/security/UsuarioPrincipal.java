package com.gamm.vinos_api.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class UsuarioPrincipal implements UserDetails {

  private final Integer idUsuario;
  private final String username;
  private final String password;
  private final Integer idSucursal;
  private final String rol;
  private final Collection<? extends GrantedAuthority> authorities;

  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }
}