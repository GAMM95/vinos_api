package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.dto.view.DistribucionDTO;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.DistribucionRowMapper;
import com.gamm.vinos_api.repository.DistribucionRepository;
import com.gamm.vinos_api.util.ResultadoSP;
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

  // Llamada al sp
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
            new SqlParameter("pPagina", Types.INTEGER),
            new SqlParameter("pLimite", Types.INTEGER),
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
      Integer pagina,
      Integer limite
  ) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdAlmacen", idAlmacen);
    params.put("pIdSucursal", idSucursal);
    params.put("pCantidad", cantidad);
    params.put("pPagina", pagina);
    params.put("pLimite", limite);

    params.put("pFechaInicio", null);
    params.put("pFechaFin", null);
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
  public ResultadoSP filtrarRepartoSucursalRango(Integer idSucursal, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Map<String, Object> params = construirParametros(2, null, idSucursal, null, pagina, limite);
    // usar helper
    addDateRange(params, "pFechaInicio", "pFechaFin", fechaInicio, fechaFin);
    return ejecutarSPConLista(spCall, params);
  }

  @Override
  public long contarRepartosSucursalRango(Integer idSucursal, LocalDate fechaInicio, LocalDate fechaFin) {
    String sql = COUNT_REPARTOS + " WHERE idSucursal = ? AND vw_distribucion.fechaDistribucion >= ? AND fechaDistribucion < DATE_ADD(?, INTERVAL 1 DAY) ";
    Long total = jdbcTemplate.queryForObject(sql, Long.class, idSucursal, fechaInicio, fechaFin);
    return total != null ? total : 0;
  }

  @Override
  public long contarRepartos() {
    Long total = jdbcTemplate.queryForObject(COUNT_REPARTOS, Long.class);
    return total != null ? total : 0;
  }

  @Override
  public List<DistribucionDTO> listarRepartosSucursal(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_DISTRIBUICION + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new DistribucionRowMapper(), limite, offset);
  }
}
