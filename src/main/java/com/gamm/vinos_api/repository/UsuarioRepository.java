package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.utils.ResultadoSP;


public interface UsuarioRepository {
  ResultadoSP registrarUsuario(Usuario usuario);  // Tipo 2 en tu SP

  Usuario obtenerPorUsername(String username);     // Para login
}
