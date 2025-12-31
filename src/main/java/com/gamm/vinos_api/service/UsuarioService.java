package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.UsuarioView;
import com.gamm.vinos_api.utils.ResultadoSP;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsuarioService {
  ResultadoSP registrarUsuario(Usuario usuario);

  ResultadoSP login(String username);

  ResultadoSP inactivarUsuario(Integer idUsuario);

  ResultadoSP activarUsuario(Integer idUsuario);

  ResultadoSP filtrarUsuario(String terminoBusqueda);

  ResultadoSP obtenerPerfil();

  ResultadoSP actualizarUsuario(Usuario usuario);

  ResultadoSP resetearPassword(Integer idUsuario, String nuevaPassword);

  ResultadoSP cambiarPassword(String actual, String nueva);

  List<UsuarioView> listarUsuarios();

  ResultadoSP actualizarFoto(Integer idUsuario, MultipartFile foto);
}
