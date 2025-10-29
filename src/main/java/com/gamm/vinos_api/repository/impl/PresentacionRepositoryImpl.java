package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.repository.PresentacionRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
            new SqlParameter("pNombre", Types.VARCHAR),
            new SqlParameter("pLitrosUnidad", Types.DOUBLE),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        );
  }

  /*** MÉTODOS CRUD ***/
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
    presentacion.setNombre("");
    presentacion.setLitrosUnidad(0.0);
    return ejecutarSP(3, presentacion);
  }

  // Listar presentaciones
  @Override
  public List<Presentacion> listarPresentaciones() {
    return jdbcTemplate.query(VW_PRESENTACIONES, new BeanPropertyRowMapper<>(Presentacion.class));
  }

  /*** MÉTODOS PRIVADOS AUXILIARES ***/
  // Ejecuta el SP con los parámetros indicados
  private ResultadoSP ejecutarSP(int tipo, Presentacion presentacion) {
    Map<String, Object> params = construirParametros(tipo, presentacion);
    return ejecutarSP(spCall, params);
  }

  // Construye el mapa de parámetros para el SP
  private Map<String, Object> construirParametros(int tipo, Presentacion presentacion) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdPresentacion", presentacion.getIdPresentacion());
    params.put("pNombre", presentacion.getNombre());
    params.put("pLitrosUnidad", presentacion.getLitrosUnidad());
    return params;
  }
}
