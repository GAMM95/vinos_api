package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.jdbc.rowmapper.UsuarioRowMapper;
import com.gamm.vinos_api.repository.UsuarioAuthRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UsuarioAuthRepositoryImpl extends BaseUsuarioSPRepository implements UsuarioAuthRepository {

  private static final String SP_USUARIO = "sp_usuario";

  public UsuarioAuthRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    initCommon(SP_USUARIO, new UsuarioRowMapper());
  }

  @Override
  public ResultadoSP registrarUsuario(Usuario usuario) {
    return ejecutarSP(construirParametros(1, usuario));
  }

  @Override
  public ResultadoSP login(String username) {
    Usuario u = new Usuario();
    u.setUsername(username);
    return ejecutarSPConLista(construirParametros(2, u),false);
  }

  @Override
  public ResultadoSP obtenerDatosPerfil(String username) {
    Usuario u = new Usuario();
    u.setUsername(username);
    return ejecutarSPConLista(construirParametros(2, u),false);
  }

  @Override
  public ResultadoSP resetearPassword(Integer idUsuario, String nuevaPassword) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);
    u.setPasswordNueva(nuevaPassword);
    return ejecutarSP(construirParametros(7, u));
  }

  @Override
  public ResultadoSP cambiarPassword(Integer idUsuario, String actual, String nueva) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);
    u.setPassword(actual);
    u.setPasswordNueva(nueva);
    return ejecutarSP(construirParametros(8, u));
  }
}
