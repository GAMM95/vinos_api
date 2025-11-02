package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.UsuarioView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface UsuarioService {
  ResultadoSP registrarUsuario(Usuario usuario);

  ResultadoSP login(String username);

  ResultadoSP inactivarUsuario(Integer idUsuario);

  ResultadoSP activarUsuario(Integer idUsuario);

  ResultadoSP filtrarUsuario(String terminoBusqueda);

  List<UsuarioView> listarUsuarios();

}
