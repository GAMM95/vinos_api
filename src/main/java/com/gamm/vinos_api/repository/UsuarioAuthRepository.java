package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.utils.ResultadoSP;

public interface UsuarioAuthRepository {
  ResultadoSP registrarUsuario(Usuario usuario);

  ResultadoSP login(String username);

}
