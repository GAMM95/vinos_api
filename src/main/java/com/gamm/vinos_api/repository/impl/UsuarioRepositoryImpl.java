package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.UsuarioRowMapper;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioRepositoryImpl extends SimpleJdbcDAOBase implements UsuarioRepository {

  private static final String SP_USUARIO = "sp_usuario";
  private static final String SP_LOGIN = "sp_login";

  private SimpleJdbcCall spCallUsuario;
  private SimpleJdbcCall spCallLogin;

  public UsuarioRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    // SP para registrar y validar usuario
    spCallUsuario = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_USUARIO)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pNombres", Types.VARCHAR),
            new SqlParameter("pApellidoPaterno", Types.VARCHAR),
            new SqlParameter("pApellidoMaterno", Types.VARCHAR),
            new SqlParameter("pUsername", Types.VARCHAR),
            new SqlParameter("pPassword", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.INTEGER),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        );

    // SP para login
    spCallLogin = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_LOGIN)
        .declareParameters(
            new SqlParameter("pUsername", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.INTEGER),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        .returningResultSet("ResultSet", new UsuarioRowMapper());
  }

  /*** Métodos públicos del repository ***/
  @Override
  public ResultadoSP registrarUsuario(Usuario usuario) {
    Map<String, Object> params = construirParametros(2, usuario);
    return ejecutarSP(spCallUsuario, params);
  }
  @Override
  public Usuario obtenerPorUsername(String username) {
    Map<String, Object> params = new HashMap<>();
    params.put("pUsername", username);

    Map<String, Object> result = spCallLogin.execute(params);

    Integer respuesta = (Integer) result.get("pRespuesta");
    if (respuesta == null || respuesta != 1) return null;

    @SuppressWarnings("unchecked")
    List<Usuario> usuarios = (List<Usuario>) result.get("ResultSet");
    if (usuarios == null || usuarios.isEmpty()) return null;

    return usuarios.getFirst();
  }

  /*** Método privado auxiliares ***/
  private Map<String, Object> construirParametros(int tipo, Usuario usuario) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pNombres", usuario.getPersona().getNombres());
    params.put("pApellidoPaterno", usuario.getPersona().getApellidoPaterno());
    params.put("pApellidoMaterno", usuario.getPersona().getApellidoMaterno());
    params.put("pUsername", usuario.getUsername());
    params.put("pPassword", usuario.getPassword());
    return params;
  }

}