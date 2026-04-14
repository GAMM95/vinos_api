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

  // el usuario DEBE estar autenticado (services protegidos)
  private static UsuarioPrincipal getPrincipalOrThrow() {
    UsuarioPrincipal up = getPrincipal();
    if (up == null) {
      throw new IllegalStateException(
          "Se intentó acceder al contexto de seguridad fuera de una sesión autenticada."
      );
    }
    return up;
  }

  // ─── Públicos — aprovechan directamente UsuarioPrincipal ─────────────────

  public static Integer getUserId() {
    return getPrincipalOrThrow().getIdUsuario();
  }

  public static String getUsername() {
    return getPrincipalOrThrow().getUsername();
  }

  public static String getNombreCompleto() {
    return getPrincipalOrThrow().getNombreCompleto();
  }

  public static Integer getSucursalId() {
    return getPrincipalOrThrow().getIdSucursal();
  }

  public static String getSucursal() {
    return getPrincipalOrThrow().getSucursal();
  }

  public static String getRol() {
    return getPrincipalOrThrow().getRol();
  }
}