package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.repository.PasswordResetTokenRepository;
import com.gamm.vinos_api.util.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PasswordResetTokenRepositoryImpl
    extends SimpleJdbcDAOBase
    implements PasswordResetTokenRepository {

  private static final String SP_TOKEN = "sp_password_reset_token";

  private SimpleJdbcCall spCall;

  public PasswordResetTokenRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_TOKEN)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdUsuario", Types.INTEGER),
            new SqlParameter("pTokenHash", Types.VARCHAR),
            new SqlOutParameter("pIdUsuarioOut", Types.INTEGER),
            new SqlOutParameter("pRespuesta", Types.INTEGER),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        );
  }

  @Override
  public ResultadoSP invalidateByUser(Integer idUsuario) {
    return ejecutarSP(spCall, params(1, idUsuario, null));
  }

  @Override
  public ResultadoSP save(Integer idUsuario, String tokenHash) {
    return ejecutarSP(spCall, params(2, idUsuario, tokenHash));
  }

  @Override
  public ResultadoSP validate(String tokenHash) {
    Map<String, Object> out = spCall.execute(params(3, null, tokenHash));

    Integer idUsuarioOut = (Integer) out.get("pIdUsuarioOut"); // <-- este es el ID del usuario
    Integer respuesta = (Integer) out.get("pRespuesta");
    String mensaje = (String) out.get("pMensaje");

    return new ResultadoSP(respuesta, mensaje, idUsuarioOut);
  }

  @Override
  public ResultadoSP markAsUsed(String tokenHash) {
    return ejecutarSP(spCall, params(4, null, tokenHash));
  }

  // 🔒 Método privado único para parámetros
  private Map<String, Object> params(
      int tipo,
      Integer idUsuario,
      String tokenHash
  ) {
    Map<String, Object> map = new HashMap<>();
    map.put("pTipo", tipo);
    map.put("pIdUsuario", idUsuario);
    map.put("pTokenHash", tokenHash);
    return map;
  }
}
