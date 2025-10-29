package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.view.CatalogoView;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CatalogoRowMapper;
import com.gamm.vinos_api.repository.CatalogoRepository;
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
public class CatalogoRepositoryImpl extends SimpleJdbcDAOBase implements CatalogoRepository {

  private static final String SP_CATALOGO = "sp_catalogo";
  private static final String VW_CATALOGOS = "SELECT * FROM vw_catalogo";

  private SimpleJdbcCall spCall;

  public CatalogoRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_CATALOGO)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdCatalogo", Types.SMALLINT),
            new SqlParameter("pIdProveedor", Types.TINYINT),
            new SqlParameter("pIdVino", Types.TINYINT),
            new SqlParameter("pIdPresentacion", Types.TINYINT),
            new SqlParameter("pPrecioUnidad", Types.DECIMAL),
            new SqlParameter("pObservacion", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        // Agregamos soporte al resultset (para filtros o listados desde SP)
        .returningResultSet("ResultSet", BeanPropertyRowMapper.newInstance(CatalogoView.class));
  }

  /*** MÉTODOS CRUD ***/
  // Registrar catalogos
  @Override
  public ResultadoSP registrarCatalogo(Catalogo catalogo) {
    return ejecutarSP(1, catalogo);
  }

  // Actualizar categorias
  @Override
  public ResultadoSP actualizarCatalogo(Catalogo catalogo) {
    return ejecutarSP(2, catalogo);
  }

// Listar catalogos
  @Override
  public List<CatalogoView> listarCatalogos() {
      return jdbcTemplate.query(VW_CATALOGOS ,new CatalogoRowMapper());
  }

  // Filtrar por codigo de algun proveedor
  @Override
  public ResultadoSP filtrarPorProveedor(Integer idProveedor) {
    Catalogo catalogo = new Catalogo();
    catalogo.setIdProveedor(idProveedor);

    Map<String, Object> params = construirParametros(3, catalogo);
    Map<String, Object> resultado = spCall.execute(params);

    @SuppressWarnings("unchecked")
    List<CatalogoView> catalogos = (List<CatalogoView>) resultado.get("ResultSet");

    // Construir el resultado del SP
    ResultadoSP res = new ResultadoSP();
    res.setCodigoRespuesta(
        resultado.get("pRespuesta") != null ? ((Number) resultado.get("pRespuesta")).intValue() : 0
    );
    res.setMensaje((String) resultado.getOrDefault("pMensaje", "Filtro compleado."));
    res.setData(catalogos);

    return res;
  }

  /*** MÉTODOS PRIVADOS AUXILIARES ***/
  // Ejecuta el procedimiento almacenado con los parámetros dados
  private ResultadoSP ejecutarSP(int tipo, Catalogo catalogo) {
    Map<String, Object> params = construirParametros(tipo, catalogo);
    return ejecutarSP(spCall, params);
  }

  // Arma los parámetros comunes del SP
  private Map<String, Object> construirParametros(int tipo, Catalogo catalogo) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdCatalogo", catalogo.getIdCatalogo());
    params.put("pIdProveedor", catalogo.getIdProveedor());
    params.put("pIdVino", catalogo.getIdVino());
    params.put("pIdPresentacion", catalogo.getIdPresentacion());
    params.put("pPrecioUnidad", catalogo.getPrecioUnidad());
    params.put("pObservacion", catalogo.getObservacion());
    return params;
  }

}