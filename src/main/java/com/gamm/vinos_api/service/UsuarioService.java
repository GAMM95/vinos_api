package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.UsuarioDTO;
import com.gamm.vinos_api.util.ResultadoSP;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsuarioService {

  // ─── Autenticación ────────────────────────────────────────────────────────
  // Retorna ResultadoSP — CustomUserDetailsService necesita manejar el fallo manualmente
  ResultadoSP login(String username);

  ResultadoSP obtenerPerfil();

  // ─── Registro y contraseñas ───────────────────────────────────────────────
  ResultadoSP registrarUsuario(Usuario usuario);

  ResultadoSP resetearPassword(Integer idUsuario, String nuevaPassword);

  ResultadoSP resetearPasswordToken(Integer idUsuario, String nuevaPassword);

  void cambiarPassword(String actual, String nueva); // void — lanza si falla

  // ─── CRUD ─────────────────────────────────────────────────────────────────
  void inactivarUsuario(Integer idUsuario);         // void — lanza si falla

  void activarUsuario(Integer idUsuario, Integer idSucursal); // void

  Usuario actualizarUsuario(Usuario usuario);        // retorna el usuario actualizado

  ResultadoSP actualizarFoto(Integer idUsuario, MultipartFile foto);

  void verificarUsername(String username, Integer idUsuario); // void — lanza si no disponible

  // ─── Consultas ────────────────────────────────────────────────────────────
  List<UsuarioDTO> listarUsuarios();

  PaginaResultado<UsuarioDTO> listarUsuariosPaginados(int pagina, int limite);

  List<UsuarioDTO> filtrarUsuario(String terminoBusqueda);

  Usuario obtenerPorId(Integer idUsuario);

  Usuario obtenerUsuarioPorEmail(String email);

}
