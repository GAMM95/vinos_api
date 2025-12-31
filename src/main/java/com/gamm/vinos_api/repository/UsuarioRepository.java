package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.UsuarioView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface UsuarioRepository {

  ResultadoSP inactivarUsuario(Integer idUsuario);

  ResultadoSP activarUsuario(Integer idUsuario);

  ResultadoSP actualizarUsuario(Usuario usuario);

  ResultadoSP filtrarUsuario(String terminoBusqueda);

  List<UsuarioView> listarUsuarios();

  ResultadoSP actualizarFoto(Integer idUsuario, String rutaFoto);

  Usuario obtenerPorId (Integer idUsuario);

  Usuario obtenerUsuarioConPassword (Integer idUsuario);
}
