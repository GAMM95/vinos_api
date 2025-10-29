package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.view.VinoView;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.VinoRowMapper;
import com.gamm.vinos_api.repository.VinoRepository;
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
public class VinoRepositoryImpl extends SimpleJdbcDAOBase implements VinoRepository {

  // Consultas SQL
  private static final String SP_VINOS = "sp_vino";
  private static final String VIEW_VINOS = "SELECT * FROM vw_vinos";

  private SimpleJdbcCall spCall;

  public VinoRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    // Configuración general del SP
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_VINOS)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdVino", Types.INTEGER),
            new SqlParameter("pNombre", Types.VARCHAR),
            new SqlParameter("pIdCategoria", Types.INTEGER),
            new SqlParameter("pPrecioVenta", Types.DOUBLE),
            new SqlParameter("pDescripcion", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        // Agregamos soporte al resultset (para filtros o listados desde SP)
        .returningResultSet("ResultSet", new VinoRowMapper());
  }

  // Registrar vino
  @Override
  public ResultadoSP registrarVino(Vino vino) {
    return ejecutarSP(1, vino);
  }

  // Actualizar vino
  @Override
  public ResultadoSP actualizarVino(Vino vino) {
    return ejecutarSP(2, vino);
  }

  // Eliminar vino
  @Override
  public ResultadoSP eliminarVinoPorId(Integer idVino) {
    Vino vino = new Vino();
    vino.setIdVino(idVino);
    return ejecutarSP(3, vino);
  }

  // filtrar vinos por nombre
  @Override
  public ResultadoSP filtrarVinoPorNombre(String nombre) {
    Vino vino = new Vino();
    vino.setNombre(nombre);

    Map<String, Object> params = construirParametros(4, vino);
    Map<String, Object> resultado = spCall.execute(params);

    // Recuperar el resultset de la vista
    @SuppressWarnings("unchecked")
    List<VinoView> vinos = (List<VinoView>) resultado.get("ResultSet");

    // Crear respuesta unificada
    ResultadoSP res = new ResultadoSP();
    res.setCodigoRespuesta(
        resultado.get("pRespuesta") != null ? ((Number) resultado.get("pRespuesta")).intValue() : 0
    );
    res.setMensaje((String) resultado.getOrDefault("pMensaje", "Búsqueda completada."));
    res.setData(vinos);

    return res;
  }

  // Listar vinos
  @Override
  public List<VinoView> listarVinos() {
    return jdbcTemplate.query(VIEW_VINOS, new VinoRowMapper());
  }

  /*** Métodos privados auxiliares ***/

  // Ejecuta el procedimiento almacenado con un tipo de operación
  private ResultadoSP ejecutarSP(int tipo, Vino vino) {
    Map<String, Object> params = construirParametros(tipo, vino);
    return ejecutarSP(spCall, params);
  }

  // Construye los parámetros para enviar al procedimiento
  private Map<String, Object> construirParametros(int tipo, Vino vino) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdVino", vino.getIdVino());
    params.put("pNombre", vino.getNombre());
    params.put("pIdCategoria", vino.getIdCategoria());
    params.put("pPrecioVenta", vino.getPrecioVenta());
    params.put("pDescripcion", vino.getDescripcion());
    return params;
  }

}
