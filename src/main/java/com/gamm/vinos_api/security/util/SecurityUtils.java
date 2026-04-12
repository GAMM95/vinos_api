package com.gamm.vinos_api.security.util;

import com.gamm.vinos_api.security.UsuarioPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

  private SecurityUtils() {
  }

  private static UsuarioPrincipal getPrincipal() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) return null;

    Object principal = auth.getPrincipal();
    return principal instanceof UsuarioPrincipal up ? up : null;
  }

  public static Integer getUserId() {
    UsuarioPrincipal up = getPrincipal();
    return up != null ? up.getIdUsuario() : null;
  }

  public static String getUsername() {
    UsuarioPrincipal up = getPrincipal();
    return up != null ? up.getUsername() : null;
  }

  public static String getNombreCompleto(){
    UsuarioPrincipal up = getPrincipal();
    return up != null ? up.getNombreCompleto() : null;
  }

  public static Integer getSucursalId() {
    UsuarioPrincipal up = getPrincipal();
    return up != null ? up.getIdSucursal() : null;
  }

  public static String getSucursal() {
    UsuarioPrincipal up = getPrincipal();
    return up != null ? up.getSucursal() : null;
  }

  public static String getRol() {
    UsuarioPrincipal up = getPrincipal();
    return up != null ? up.getRol() : null;
  }
}