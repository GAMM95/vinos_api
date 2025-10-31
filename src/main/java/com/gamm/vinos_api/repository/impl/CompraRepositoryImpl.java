package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.view.CompraView;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CompraRowMapper;
import com.gamm.vinos_api.repository.CompraRepository;
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
public class CompraRepositoryImpl extends SimpleJdbcDAOBase implements CompraRepository {
  private static final String SP_COMPRA = "sp_compra";
  private static final String VW_COMPRAS = "SELECT * FROM vw_compras"; // si tienes vista
  private SimpleJdbcCall spCall;

  public CompraRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_COMPRA)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdCompra", Types.INTEGER),
            new SqlParameter("pIdProveedor", Types.TINYINT),
            new SqlParameter("pCostoEmbalaje", Types.DECIMAL),
            new SqlParameter("pCostoEnvioAgencia", Types.DECIMAL),
            new SqlParameter("pCostoTransporte", Types.DECIMAL),
            new SqlParameter("pObservaciones", Types.VARCHAR),
            new SqlParameter("pIdCatalogo", Types.SMALLINT),
            new SqlParameter("pCantidadGalones", Types.TINYINT),
            new SqlParameter("pNombreProveedor", Types.VARCHAR),
            new SqlParameter("pFechaInicio", Types.DATE),
            new SqlParameter("pFechaFin", Types.DATE),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        .returningResultSet("ResultSet", new CompraRowMapper());
  }

  @Override
  public ResultadoSP crearCompra(Compra compra) {
    return ejecutarSP(1, compra);
  }

  @Override
  public ResultadoSP confirmarCompra(Compra compra) {
    return ejecutarSP(2, compra);
  }

  @Override
  public ResultadoSP actualizarCompra(Compra compra) {
    return ejecutarSP(3, compra);
  }

  @Override
  public ResultadoSP anularCompra(Integer idCompra) {
    Compra compra = new Compra();
    compra.setIdCompra(idCompra);
    return ejecutarSP(4, compra);
  }

  @Override
  public ResultadoSP reactivarCompra(Integer idCompra) {
    Compra compra = new Compra();
    compra.setIdCompra(idCompra);
    return ejecutarSP(5, compra);
  }

  @Override
  public ResultadoSP buscarCompras(Compra compra) {
    ResultadoSP res = ejecutarSP(6, compra);

    List<CompraView> lista = getResultList(res);

    res.setData(lista);
    return res;
  }

  @Override
  public List<CompraView> listarCompras() {
    return jdbcTemplate.query(VW_COMPRAS, new CompraRowMapper());
  }

  /*** Métodos privados auxiliares ***/
  private ResultadoSP ejecutarSP(int tipo, Compra compra) {
    Map<String, Object> params = construirParametros(tipo, compra);
    return ejecutarSP(spCall, params);
  }

  private Map<String, Object> construirParametros(int tipo, Compra compra) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdCompra", compra.getIdCompra());
    params.put("pIdProveedor", compra.getIdProveedor());
    params.put("pCostoEmbalaje", compra.getCostoEmbalaje());
    params.put("pCostoEnvioAgencia", compra.getCostoEnvioAgencia());
    params.put("pCostoTransporte", compra.getCostoTransporte());
    params.put("pObservaciones", compra.getObservaciones());
    params.put("pIdCatalogo", compra.getIdCatalogo());
    params.put("pCantidadGalones", compra.getCantidadGalones());
    params.put("pNombreProveedor", compra.getNombreProveedor());
    params.put("pFechaInicio", compra.getFechaInicio());
    params.put("pFechaFin", compra.getFechaFin());
    return params;
  }
}
