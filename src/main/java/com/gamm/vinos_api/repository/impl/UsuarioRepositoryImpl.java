package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.UsuarioView;
import com.gamm.vinos_api.jdbc.rowmapper.UsuarioViewRowMapper;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioRepositoryImpl extends BaseUsuarioSPRepository implements UsuarioRepository {

  private static final String SP_USUARIO = "sp_usuario";
  private static final String VIEW_USUARIOS = "SELECT * FROM vw_usuarios";

  public UsuarioRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    initCommon(SP_USUARIO, new UsuarioViewRowMapper());
  }

  @Override
  public ResultadoSP inactivarUsuario(Integer idUsuario) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);
    return ejecutarSP(3, u);
  }

  @Override
  public ResultadoSP activarUsuario(Integer idUsuario) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);
    return ejecutarSP(4, u);
  }

  @Override
  public ResultadoSP filtrarUsuario(String terminoBusqueda) {
    Usuario u = new Usuario();
    u.setUsername(terminoBusqueda);

    ResultadoSP res = ejecutarSP(5, u);
    List<UsuarioView> lista = getResultList(res);
    res.setData(lista);
    return res;
  }

  @Override
  public List<UsuarioView> listarUsuarios() {
    return jdbcTemplate.query(VIEW_USUARIOS, new UsuarioViewRowMapper());
  }

  /** Construye parámetros y ejecuta el SP */
  private ResultadoSP ejecutarSP(int tipo, Usuario usuario) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdUsuario", usuario.getIdUsuario());
    params.put("pUsername", usuario.getUsername());
    params.put("pNombres", null);
    params.put("pApellidoPaterno", null);
    params.put("pApellidoMaterno", null);
    params.put("pPassword", null);
    params.put("pTerminoBusqueda", usuario.getUsername());

    return ejecutar(params);
  }
}
