package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.utils.ResultadoSP;

public interface UsuarioAuthRepository {
  ResultadoSP registrarUsuario(Usuario usuario);

  ResultadoSP login(String username);

  ResultadoSP obtenerDatosPerfil(String username);

  ResultadoSP resetearPassword(Integer idUsuario, String nuevaPassword);

  ResultadoSP cambiarPassword(Integer idUsuario, String passwordActual, String passwordNueva);

}
