package com.gamm.vinos_api.jdbc;

import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public abstract class SimpleJdbcDAOBase {

  private static final String MENSAJE_DEFECTO = "Operación realizada correctamente.";

  protected final DataSource dataSource;
  protected final JdbcTemplate jdbcTemplate;
  protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  // Constructor único
  public SimpleJdbcDAOBase(DataSource dataSource) {
    this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  /// Metodo general para ejecutar SP
  protected ResultadoSP ejecutarSP(
      SimpleJdbcCall spCall,
      Map<String, Object> params,
      String resultKey
  ) {
    try {
      Map<String, Object> resultado = spCall.execute(params);

      ResultadoSP resultadoSP = new ResultadoSP();
      resultadoSP.setCodigoRespuesta(
          resultado.get("pRespuesta") != null
              ? ((Number) resultado.get("pRespuesta")).intValue()
              : 0
      );
      resultadoSP.setMensaje((String) resultado.getOrDefault("pMensaje", MENSAJE_DEFECTO));

      if (resultKey != null && resultado.containsKey(resultKey)) {
        @SuppressWarnings("unchecked")
        List<Object> data = (List<Object>) resultado.get(resultKey);
        resultadoSP.setData(data);
      }
      return resultadoSP;
    } catch (Exception e) {
      ResultadoSP resultadoSP = new ResultadoSP();
      resultadoSP.setCodigoRespuesta(-1);
      resultadoSP.setMensaje("Error interno en la base de datos.");
      if (resultKey != null) resultadoSP.setData(Collections.emptyList());
      return resultadoSP;
    }
  }

  /// Metodos por conveniencia
  protected ResultadoSP ejecutarSP(SimpleJdbcCall spCall, Map<String, Object> params) {
    return  ejecutarSP(spCall,params,null);
  }

  protected <T> ResultadoSP ejecutarSPConLista(SimpleJdbcCall spCall, Map<String, Object> params) {
    return ejecutarSP(spCall, params, "ResultSet");
  }

  @SuppressWarnings("unchecked")
  protected <T> List<T> getResultList(ResultadoSP res) {
    return (List<T>) res.getData();
  }
}
