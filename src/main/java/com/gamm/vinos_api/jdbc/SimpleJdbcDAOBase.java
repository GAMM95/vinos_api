package com.gamm.vinos_api.jdbc;

import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public abstract class SimpleJdbcDAOBase {

  /** CONSTANTES ESTÁNDAR */
  protected static final String MENSAJE_DEFECTO = "Operación realizada correctamente.";
  protected static final String PARAM_RESPUESTA = "pRespuesta";
  protected static final String PARAM_MENSAJE = "pMensaje";
  protected static final String DEFAULT_RESULTSET  = "ResultSet";

  /** INFRAESTRUCTURA JDBC */
  protected final DataSource dataSource;
  protected final JdbcTemplate jdbcTemplate;
  protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  // Constructor único
  public SimpleJdbcDAOBase(DataSource dataSource) {
    this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  /// EJECUCIÓN GENERAL DE STORED PROCEDURES
  protected ResultadoSP ejecutarSP(
      SimpleJdbcCall spCall,
      Map<String, Object> params,
      String resultKey
  ) {
    try {
      Map<String, Object> resultado = spCall.execute(params);

      ResultadoSP resultadoSP = new ResultadoSP();
      resultadoSP.setCodigoRespuesta(
          resultado.get(PARAM_RESPUESTA) != null
              ? ((Number) resultado.get(PARAM_RESPUESTA)).intValue()
              : 0
      );
      resultadoSP.setMensaje((String) resultado.getOrDefault(PARAM_MENSAJE, MENSAJE_DEFECTO));

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

  /// MÉTODOS DE CONVENIENCIA
  protected ResultadoSP ejecutarSP(SimpleJdbcCall spCall, Map<String, Object> params) {
    return  ejecutarSP(spCall,params,null);
  }

  protected <T> ResultadoSP ejecutarSPConLista(SimpleJdbcCall spCall, Map<String, Object> params) {
    return ejecutarSP(spCall, params, "ResultSet");
  }

  @SuppressWarnings("unchecked")
  protected <T> List<T> getResultList(ResultadoSP res) {
    return res != null && res.getData() != null
        ? (List<T>) res.getData()
        : Collections.emptyList();
  }

  // =====================================================
  // HELPERS PARA RANGO DE FECHAS
  // =====================================================

  /**
   * Rango de fechas usando LocalDate
   * Inicio -> 00:00:00
   * Fin    -> 23:59:59
   */
  protected void addDateRange(
      Map<String, Object> params,
      String fechaInicioKey,
      String fechaFinKey,
      LocalDate fechaInicio,
      LocalDate fechaFin
  ) {
    params.put(
        fechaInicioKey,
        fechaInicio != null ? fechaInicio.atStartOfDay() : null
    );

    params.put(
        fechaFinKey,
        fechaFin != null ? fechaFin.atTime(23, 59, 59) : null
    );
  }

  /**
   * Rango de fechas usando LocalDateTime directamente
   */
  protected void addDateTimeRange(
      Map<String, Object> params,
      String fechaInicioKey,
      String fechaFinKey,
      LocalDateTime fechaInicio,
      LocalDateTime fechaFin
  ) {
    params.put(fechaInicioKey, fechaInicio);
    params.put(fechaFinKey, fechaFin);
  }
}
