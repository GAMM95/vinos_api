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
    return ejecutarSPConLista(spCall, construirParametros(5, u));
  }

  @Override
  public List<UsuarioView> listarUsuarios() {
    return jdbcTemplate.query(VIEW_USUARIOS, new UsuarioViewRowMapper());
  }

  private ResultadoSP ejecutarSP(int tipo, Usuario usuario) {
    Map<String, Object> params = construirParametros(tipo, usuario);
    return ejecutarSP(spCall, params);
  }

  private Map<String, Object> construirParametros(int tipo, Usuario u) {
    Map<String, Object> p = new HashMap<>();
    p.put("pTipo", tipo);
    p.put("pIdUsuario", u.getIdUsuario());
    p.put("pUsername", u.getUsername());

    // Evitar NPE si persona es null
    if (u.getPersona() != null) {
      p.put("pNombres", u.getPersona().getNombres());
      p.put("pApellidoPaterno", u.getPersona().getApellidoPaterno());
      p.put("pApellidoMaterno", u.getPersona().getApellidoMaterno());
    } else {
      p.put("pNombres", null);
      p.put("pApellidoPaterno", null);
      p.put("pApellidoMaterno", null);
    }

    p.put("pPassword", u.getPassword());
    p.put("pTerminoBusqueda", u.getUsername());

    return p;
  }
}
