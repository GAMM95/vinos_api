package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.jdbc.rowmapper.UsuarioRowMapper;
import com.gamm.vinos_api.repository.UsuarioAuthRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    return ejecutarSP(1, usuario);
  }

  @Override
  public ResultadoSP login(String username) {
    Usuario u = new Usuario();
    u.setUsername(username);
    return ejecutarSP(2, u);
  }

  // Parámetros según el tipo de operación y ejecuta SP
  private ResultadoSP ejecutarSP(int tipo, Usuario usuario) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);

    if (tipo == 1 && usuario.getPersona() != null) {
      params.put("pNombres", usuario.getPersona().getNombres());
      params.put("pApellidoPaterno", usuario.getPersona().getApellidoPaterno());
      params.put("pApellidoMaterno", usuario.getPersona().getApellidoMaterno());
      params.put("pPassword", usuario.getPassword());
    } else {
      params.put("pNombres", null);
      params.put("pApellidoPaterno", null);
      params.put("pApellidoMaterno", null);
      params.put("pPassword", null);
    }

    params.put("pUsername", usuario.getUsername());
    params.put("pIdUsuario", usuario.getIdUsuario());
    params.put("pTerminoBusqueda", null);

    Map<String, Object> result = spCall.execute(params);
    Integer respuesta = (Integer) result.get("pRespuesta");
    String mensaje = (String) result.get("pMensaje");
    @SuppressWarnings("unchecked")
    List<Usuario> usuarios = (List<Usuario>) result.get("ResultSet");
    Usuario u = (usuarios != null && !usuarios.isEmpty()) ? usuarios.getFirst() : null;
    return new ResultadoSP(respuesta != null ? respuesta : 0, mensaje, u);
  }
}
