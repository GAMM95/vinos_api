package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.view.UsuarioView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.util.ResultadoSP;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsuarioService {
  ResultadoSP registrarUsuario(Usuario usuario);

  ResultadoSP login(String username);

  ResultadoSP inactivarUsuario(Integer idUsuario);

  ResultadoSP activarUsuario(Integer idUsuario, Integer idSucursal);

  ResultadoSP filtrarUsuario(String terminoBusqueda);

  ResultadoSP obtenerPerfil();

  ResultadoSP actualizarUsuario(Usuario usuario);

  ResultadoSP resetearPassword(Integer idUsuario, String nuevaPassword);

  ResultadoSP resetearPasswordToken(Integer idUsuario, String nuevaPassword);

  ResultadoSP cambiarPassword(String actual, String nueva);

  List<UsuarioView> listarUsuarios();

  ResponseVO listarUsuariosPaginados(int pagina, int limite);

  ResultadoSP actualizarFoto(Integer idUsuario, MultipartFile foto);

  ResultadoSP verificarUsername(String username, Integer idUsuario);

  Usuario obtenerPorId(Integer idUsuario);

  Usuario obtenerUsuarioPorEmail(String email);

}
