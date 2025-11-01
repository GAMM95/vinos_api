package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.utils.ResultadoSP;

public interface UsuarioService {
  ResultadoSP registrarUsuario(Usuario usuario);

  Usuario obtenerPorUsername(String username);
}
