package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.domain.view.PresentacionView;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.PresentacionRowMapper;
import com.gamm.vinos_api.repository.PresentacionRepository;
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
public class PresentacionRepositoryImpl extends SimpleJdbcDAOBase implements PresentacionRepository {

  // Consultas sql
  private static final String SP_PRESENTACION = "sp_presentacion";
  private static final String VW_PRESENTACIONES = "SELECT * FROM vw_presentaciones";

  private SimpleJdbcCall spCall;

  public PresentacionRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_PRESENTACION)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdPresentacion", Types.TINYINT),
            new SqlParameter("pDescripcion", Types.VARCHAR),
            new SqlParameter("pVolumen", Types.DOUBLE),
            new SqlParameter("pUnidadVolumen", Types.TINYINT),
            new SqlOutParameter(PARAM_RESPUESTA, Types.TINYINT),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        )
    .returningResultSet("ResultSet", new PresentacionRowMapper());
  }

  /// MÉTODOS CRUD
  // Registrar presentaciones de vinos
  @Override
  public ResultadoSP guardarPresentacion(Presentacion presentacion) {
    return ejecutarSP(1, presentacion);
  }

  // Actualizar presentaciones
  @Override
  public ResultadoSP actualizarPresentacion(Presentacion presentacion) {
    return ejecutarSP(2, presentacion);
  }

  // Dar de baja o eliminar presentaciones
  @Override
  public ResultadoSP darBaja(Integer idPresentacion) {
    Presentacion presentacion = new Presentacion();
    presentacion.setIdPresentacion(idPresentacion);
    return ejecutarSP(3, presentacion);
  }

  // Dar de alta
  @Override
  public ResultadoSP darAlta(Integer idPresentacion) {
    Presentacion presentacion = new Presentacion();
    presentacion.setIdPresentacion(idPresentacion);
    return ejecutarSP(4, presentacion);
  }

  // Filtrar presentaciones por descripción
  @Override
  public ResultadoSP filtrarPresentacion(String descripcion) {
    Presentacion p = new Presentacion();
    p.setDescripcion(descripcion);
    return ejecutarSPConLista(spCall, construirParametros(5, p));
  }

  // Listar presentaciones
  @Override
  public List<PresentacionView> listarPresentaciones() {
    return jdbcTemplate.query(VW_PRESENTACIONES, new PresentacionRowMapper());
  }

  /// MÉTODOS PRIVADOS AUXILIARES
  // Ejecuta el SP con los parámetros indicados
  private ResultadoSP ejecutarSP(int tipo, Presentacion presentacion) {
    return ejecutarSP(spCall, construirParametros(tipo, presentacion));
  }

  // Construye el mapa de parámetros para el SP
  private Map<String, Object> construirParametros(int tipo, Presentacion presentacion) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdPresentacion", presentacion.getIdPresentacion());
    params.put("pDescripcion", presentacion.getDescripcion());
    params.put("pVolumen", presentacion.getVolumen());
    if (presentacion.getUnidadVolumen() != null) {
      params.put("pUnidadVolumen", presentacion.getUnidadVolumen().getIdUnidadVolumen());
    } else {
      params.put("pUnidadVolumen", null);
    }
    return params;
  }

}