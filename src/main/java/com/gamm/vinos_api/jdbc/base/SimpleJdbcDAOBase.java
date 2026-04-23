package com.gamm.vinos_api.jdbc.base;

import com.gamm.vinos_api.util.ResultadoSP;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public abstract class SimpleJdbcDAOBase {

  /* Constantes estándar */
  protected static final String MENSAJE_DEFECTO = "Operación realizada correctamente.";
  protected static final String PARAM_RESPUESTA = "pRespuesta";
  protected static final String PARAM_MENSAJE = "pMensaje";
  protected static final String DEFAULT_RESULTSET = "ResultSet";


  /* Infraestructura jdbc */
  protected final DataSource dataSource;
  protected final JdbcTemplate jdbcTemplate;
  protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  protected SimpleJdbcDAOBase(DataSource dataSource) {
    this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  // ─── Ejecución de SP ─────────────────────────────────────────────────────

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
      resultadoSP.setMensaje(
          (String) resultado.getOrDefault(PARAM_MENSAJE, MENSAJE_DEFECTO)
      );

      if (resultKey != null && resultado.containsKey(resultKey)) {
        @SuppressWarnings("unchecked")
        List<Object> data = (List<Object>) resultado.get(resultKey);
        resultadoSP.setData(data);
      }
      return resultadoSP;

    } catch (Exception e) {
      log.error("Error ejecutando SP [{}]: {}", spCall.getProcedureName(), e.getMessage(), e);
      ResultadoSP error = new ResultadoSP();
      error.setCodigoRespuesta(-1);
      error.setMensaje("Error interno en la base de datos.");
      if (resultKey != null) error.setData(Collections.emptyList());
      return error;
    }
  }

  // Métodos de conveniencia
  protected ResultadoSP ejecutarSP(SimpleJdbcCall spCall, Map<String, Object> params) {
    return ejecutarSP(spCall, params, null);
  }

  protected <T> ResultadoSP ejecutarSPConLista(SimpleJdbcCall spCall, Map<String, Object> params) {
    return ejecutarSP(spCall, params, DEFAULT_RESULTSET);
  }

  // Útil cuando solo necesitas los datos y no el código de respuesta del SP
  @SuppressWarnings("unchecked")
  protected <T> List<T> ejecutarSPYObtenerLista(SimpleJdbcCall spCall, Map<String, Object> params) {
    try {
      Map<String, Object> resultado = spCall.execute(params);
      List<T> data = (List<T>) resultado.get(DEFAULT_RESULTSET);
      return data != null ? data : Collections.emptyList();
    } catch (Exception e) {
      log.error("Error ejecutando SP [{}]: {}", spCall.getProcedureName(), e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  @SuppressWarnings("unchecked")
  protected <T> List<T> getResultList(ResultadoSP res) {
    return res != null && res.getData() != null
        ? (List<T>) res.getData()
        : Collections.emptyList();
  }

  // ─── Helpers de fechas ────────────────────────────────────────────────────

  protected void addDateRange(
      Map<String, Object> params,
      String fechaInicioKey,
      String fechaFinKey,
      LocalDate fechaInicio,
      LocalDate fechaFin
  ) {
    params.put(fechaInicioKey, fechaInicio != null ? fechaInicio.atStartOfDay() : null);
    params.put(fechaFinKey, fechaFin != null ? fechaFin.atTime(23, 59, 59) : null);
  }

  // Rango de fechas usando LocalDateTime directamente
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

  // ─── Paginación en memoria ────────────────────────────────────────────────
  protected <T> List<T> paginarLista(List<T> lista, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    if (offset >= lista.size()) return Collections.emptyList();
    return lista.subList(offset, Math.min(offset + limite, lista.size()));
  }



}