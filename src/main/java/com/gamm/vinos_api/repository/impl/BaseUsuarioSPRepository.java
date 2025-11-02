package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.utils.ResultadoSP;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

public abstract class BaseUsuarioSPRepository extends SimpleJdbcDAOBase {

  protected SimpleJdbcCall spCall;

  protected BaseUsuarioSPRepository(DataSource dataSource) {
    super(dataSource);
  }

  // Inicializa el SimpleJdbcCall con parámetros comunes y RowMapper
  protected void initCommon(String spName, RowMapper<?> mapper) {
    this.spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(spName)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pNombres", Types.VARCHAR),
            new SqlParameter("pApellidoPaterno", Types.VARCHAR),
            new SqlParameter("pApellidoMaterno", Types.VARCHAR),
            new SqlParameter("pIdUsuario", Types.INTEGER),
            new SqlParameter("pUsername", Types.VARCHAR),
            new SqlParameter("pPassword", Types.VARCHAR),
            new SqlParameter("pTerminoBusqueda", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.INTEGER),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        .returningResultSet("ResultSet", mapper);
  }

  // Ejecuta el SP con los parámetros ya construidos
  protected ResultadoSP ejecutar(Map<String, Object> params) {
    Map<String, Object> result = spCall.execute(params);
    Integer r = (Integer) result.get("pRespuesta");
    String m = (String) result.get("pMensaje");
    return new ResultadoSP(r != null ? r : 0, m, result.get("ResultSet"));
  }
}
