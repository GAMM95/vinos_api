package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.dto.view.MovimientosView;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.MovimientosRowMapper;
import com.gamm.vinos_api.repository.MovimientoRepository;
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
public class MovimientosRepositoryImpl extends SimpleJdbcDAOBase implements MovimientoRepository {

  private static final String SP_MOVIMIENTOS = "sp_movimientos";
  //Querie para usar la vista de movimientos
  private static final String VW_MOVIMIENTOS = "SELECT * FROM vw_movimientos";
  private static final String VW_MIS_MOVIMIENTOS = "SELECT * FROM vw_movimientos WHERE idUsuario = ?";

  // Queries para listar los detalles de cada caja
  private static final String VW_DETALLE_MOVIMIENTOS_USUARIO = "SELECT * FROM vw_movimientos WHERE idUsuario = ? AND idCaja = ? ORDER BY idMovimiento";
  private static final String VW_DETALLE_MOVIMIENTOS = "SELECT * FROM vw_movimientos WHERE idCaja = ? ORDER BY idMovimiento";
  // Queries para contar movimientos
  private static final String COUNT_MOVIMIENTOS = "SELECT COUNT(*) FROM vw_movimientos";
  private static final String COUNT_MIS_MOVIMIENTOS = "SELECT COUNT(*) FROM vw_movimientos WHERE idUsuario = ?";
  private SimpleJdbcCall spCall;

  public MovimientosRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName(SP_MOVIMIENTOS)
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("pTipoOperacion", Types.TINYINT),
            new SqlParameter("pIdCaja", Types.INTEGER),
            new SqlParameter("pIdUsuario", Types.INTEGER),
            new SqlParameter("pFechaInicio", Types.DATE),
            new SqlParameter("pFechaFin", Types.DATE),
            new SqlParameter("pPagina", Types.INTEGER),
            new SqlParameter("pLimite", Types.INTEGER),
            new SqlOutParameter(PARAM_RESPUESTA, Types.TINYINT),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        )
        .returningResultSet(DEFAULT_RESULTSET, new MovimientosRowMapper());
  }

  /* Helpers */
  private Map<String, Object> params(
      int tipo,
      Integer idCaja,
      Integer idUsuario,
      LocalDate fechaInicio,
      LocalDate fechaFin,
      Integer pagina,
      Integer limite
  ) {
    Map<String, Object> p = new HashMap<>();
    p.put("pTipoOperacion", tipo);
    p.put("pIdCaja", idCaja);
    p.put("pIdUsuario", idUsuario);
    p.put("pFechaInicio", fechaInicio);
    p.put("pFechaFin", fechaFin);
    p.put("pPagina", pagina);
    p.put("pLimite", limite);
    return p;
  }

  @Override
  public ResultadoSP filtrarMisMovimientosPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    return ejecutarSPConLista(spCall, params(1, null, idUsuario, fechaInicio, fechaFin, pagina, limite));
  }

  @Override
  public long contarMisMovimientosPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
    String sql = COUNT_MIS_MOVIMIENTOS + " AND fechaMovimiento >= ? AND fechaMovimiento < DATE_ADD(?, INTERVAL 1 DAY)";
    Long total = jdbcTemplate.queryForObject(sql, Long.class, idUsuario, fechaInicio, fechaFin);
    return total != null ? total : 0;
  }

  @Override
  public ResultadoSP filtrarMovimientosPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    return ejecutarSPConLista(spCall, params(2, null, idUsuario, fechaInicio, fechaFin, pagina, limite));
  }

  @Override
  public long contarMovimientosPorRango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
    String sql = COUNT_MOVIMIENTOS + " WHERE idUsuario = ? AND fechaMovimiento >= ? AND fechaMovimiento < DATE_ADD(?, INTERVAL 1 DAY)";
    Long total = jdbcTemplate.queryForObject(sql, Long.class, idUsuario, fechaInicio, fechaFin);
    return total != null ? total : 0;
  }

  @Override
  public long contarMisMovimientos(Integer idUsuario) {
    Long total = jdbcTemplate.queryForObject(COUNT_MIS_MOVIMIENTOS, Long.class, idUsuario);
    return total != null ? total : 0;
  }

  @Override
  public List<MovimientosView> listarMisMovimientos(Integer idUsuario, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_MIS_MOVIMIENTOS + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new MovimientosRowMapper(), idUsuario, limite, offset);
  }

  @Override
  public long contarTotalMovimientos() {
    Long total = jdbcTemplate.queryForObject(COUNT_MOVIMIENTOS, Long.class);
    return total != null ? total : 0;
  }

  @Override
  public List<MovimientosView> listarTotalMovimientos(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_MOVIMIENTOS + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new MovimientosRowMapper(), limite, offset);
  }

  @Override
  public List<MovimientosView> listarDetalleMovimientoUsuario(Integer idUsuario, Integer idCaja) {
    return jdbcTemplate.query(VW_DETALLE_MOVIMIENTOS_USUARIO, new MovimientosRowMapper(), idUsuario, idCaja);
  }

  @Override
  public List<MovimientosView> listarDetalleMovimiento(Integer idCaja) {
    return jdbcTemplate.query(VW_DETALLE_MOVIMIENTOS, new MovimientosRowMapper(), idCaja);
  }


  @Override
  public ResultadoSP filtrarPorCaja(Integer idCaja) {
    return ejecutarSPConLista(spCall, params(3, idCaja, null, null, null, null, null));
  }
}

