package com.gamm.vinos_api.service;

import com.gamm.vinos_api.security.util.SecurityUtils;

public abstract class BaseService {

  // ✅ SecurityUtils lanza IllegalStateException si no hay sesión
  protected Integer getIdUsuarioAutenticado() {
    return SecurityUtils.getUserId();
  }

  protected String getNombreUsuarioAutenticado() {
    return SecurityUtils.getNombreCompleto();
  }

  protected String getUsernameAutenticado() {
    return SecurityUtils.getUsername();
  }

  protected String getRolAutenticado() {
    return SecurityUtils.getRol();
  }

  // getSucursalId() puede retornar null si el usuario no tiene sucursal
  // por eso este método sí valida — es una regla de negocio, no de sesión
  protected Integer getIdSucursalAutenticada() {
    Integer idSucursal = SecurityUtils.getSucursalId();
    if (idSucursal == null) {
      throw new IllegalStateException("El usuario no tiene sucursal asignada.");
    }
    return idSucursal;
  }

  protected String getNombreSucursalAutenticado() {
    return SecurityUtils.getSucursal();
  }

  protected boolean esAdministrador() {
    return "ADMINISTRADOR".equalsIgnoreCase(getRolAutenticado());
  }
}