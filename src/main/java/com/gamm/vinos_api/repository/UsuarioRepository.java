package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.view.UsuarioDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface UsuarioRepository {

  ResultadoSP inactivarUsuario(Integer idUsuario);

  ResultadoSP activarUsuario(Integer idUsuario, Integer idSucursal);

  ResultadoSP actualizarUsuario(Usuario usuario);

  ResultadoSP filtrarUsuario(String terminoBusqueda);

  List<UsuarioDTO> listarUsuarios();

  List<UsuarioDTO> listarUsuariosPaginados(int pagina, int limite);

  Long contarUsuarios();

  ResultadoSP actualizarFoto(Integer idUsuario, String rutaFoto);

  Usuario obtenerPorId(Integer idUsuario);

  Usuario obtenerUsuarioConPassword(Integer idUsuario);

  ResultadoSP verificarUsername(String username, Integer idUsuario);

  Usuario obtenerUsuarioPorEmail(String email);

  List<UsuarioDTO> listarPorRolYSucursal(String rol, Integer idSucursal);

  UsuarioDTO obtenerUsuarioPorId(Integer idUsuario);
}
