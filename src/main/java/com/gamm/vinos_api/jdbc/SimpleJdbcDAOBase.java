package com.gamm.vinos_api.jdbc;

import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.Map;

@Getter
public abstract class SimpleJdbcDAOBase {

  protected final DataSource dataSource;
  protected final JdbcTemplate jdbcTemplate;
  protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  // Constructor único
  public SimpleJdbcDAOBase(DataSource dataSource) {
    this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  /**
   * Ejecuta un procedimiento almacenado y retorna un objeto ResultadoSP estandarizado.
   */
  protected ResultadoSP ejecutarSP(SimpleJdbcCall spCall, Map<String, Object> params) {
    Map<String, Object> resultado = spCall.execute(params);

    Integer codigo = resultado.get("pRespuesta") != null
        ? ((Number) resultado.get("pRespuesta")).intValue()
        : 0;

    String mensaje = (String) resultado.getOrDefault("pMensaje", "Operación exitosa.");

    ResultadoSP res = new ResultadoSP();
    res.setCodigoRespuesta(codigo);
    res.setMensaje(mensaje);

    // Si el SP devuelve un ResultSet (por ejemplo, en búsquedas o reportes)
    if (resultado.containsKey("ResultSet")) {
      res.setData(resultado.get("ResultSet"));
    }

    return res;
  }
}
