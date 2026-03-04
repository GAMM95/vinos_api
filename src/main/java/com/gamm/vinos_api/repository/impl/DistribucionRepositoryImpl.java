package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.domain.view.CajaView;
import com.gamm.vinos_api.domain.view.DistribucionView;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CajaRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.DistribucionRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.PresentacionRowMapper;
import com.gamm.vinos_api.repository.DistribucionRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DistribucionRepositoryImpl extends SimpleJdbcDAOBase implements DistribucionRepository {

  // Procedimiento almacenado
  private static final String SP_DISTRIBUCION = "sp_distribuir_mercaderia";

  // Query para listar todas las distribuciones de productos de almacén
  private static final String COUNT_REPARTOS = "SELECT COUNT(*) FROM vw_distribucion";
  private static final String VW_DISTRIBUICION = "SELECT * FROM vw_distribucion";

  private SimpleJdbcCall spCall;

  public DistribucionRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_DISTRIBUCION)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdAlmacen", Types.INTEGER),
            new SqlParameter("pIdSucursal", Types.TINYINT),
            new SqlParameter("pCantidad", Types.INTEGER),
            new SqlParameter("pFechaInicio", Types.DATE),
            new SqlParameter("pFechaFin", Types.DATE),
            new SqlOutParameter(PARAM_RESPUESTA, Types.TINYINT),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        )
        .returningResultSet(DEFAULT_RESULTSET, new DistribucionRowMapper());
  }

  /* Helpers */
  private Map<String, Object> construirParametros(
      int tipo,
      Integer idAlmacen,
      Integer idSucursal,
      Integer cantidad,
      LocalDate fechaInicio,
      LocalDate fechaFin
  ) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdAlmacen", idAlmacen);
    params.put("pIdSucursal", idSucursal);
    params.put("pCantidad", cantidad);
    params.put("pFechaInicio", fechaInicio);
    params.put("pFechaFin", fechaFin);
    return params;
  }

  @Override
  public ResultadoSP distribuirProducto(DistribucionSucursal distribucionSucursal) {
    Map<String, Object> params = construirParametros(
        1,
        distribucionSucursal.getAlmacen().getIdAlmacen(),
        distribucionSucursal.getSucursal().getIdSucursal(),
        distribucionSucursal.getCantidad(),
        null,
        null
    );
    return ejecutarSP(spCall, params);
  }

  @Override
  public long contarRepartos() {
    Long total = jdbcTemplate.queryForObject(COUNT_REPARTOS, Long.class);
    return total != null ? total : 0;
  }

  @Override
  public List<DistribucionView> listarRepartosSucursal(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_DISTRIBUICION + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new DistribucionRowMapper(), limite, offset);
  }
}
