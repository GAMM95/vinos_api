package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.dto.view.CajaView;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CajaRowMapper;
import com.gamm.vinos_api.repository.CajaRepository;
import com.gamm.vinos_api.util.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CajaRepositoryImpl extends SimpleJdbcDAOBase implements CajaRepository {

  private static final String SP_CAJA = "sp_caja";

  private static final String COUNT_MIS_CAJAS =
      "SELECT COUNT(*) FROM vw_caja WHERE idUsuario = ?";

  private static final String VW_MIS_CAJAS =
      "SELECT * FROM vw_caja WHERE idUsuario = ?";

  private static final String COUNT_CAJAS =
      "SELECT COUNT(*) FROM vw_caja";

  private static final String VW_CAJAS =
      "SELECT * FROM vw_caja";

  private static final String VW_ULTIMA_CAJA_ABIERTA =
      "SELECT * FROM vw_ultima_caja_abierta_usuario WHERE idUsuario = ?";

  private SimpleJdbcCall spCall;

  public CajaRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_CAJA)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdCaja", Types.INTEGER),
            new SqlParameter("pIdSucursal", Types.INTEGER),
            new SqlParameter("pIdUsuario", Types.INTEGER),
            new SqlParameter("pSaldoInicial", Types.DECIMAL),
            new SqlParameter("pFechaInicio", Types.DATE),
            new SqlParameter("pFechaFin", Types.DATE),
            new SqlParameter("pPagina", Types.INTEGER),
            new SqlParameter("pLimite", Types.INTEGER),
            new SqlOutParameter("pCodCaja", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        .returningResultSet("ResultSet", new CajaRowMapper());
  }

  /* =========================
     Helpers
     ========================= */

  private Map<String, Object> crearParametros(
      int tipo,
      Integer idCaja,
      Integer idSucursal,
      Integer idUsuario,
      BigDecimal saldoInicial,
      LocalDate fechaInicio,
      LocalDate fechaFin,
      Integer pagina,
      Integer limite
  ) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdCaja", idCaja);
    params.put("pIdSucursal", idSucursal);
    params.put("pIdUsuario", idUsuario);
    params.put("pSaldoInicial", saldoInicial);
    params.put("pFechaInicio", fechaInicio);
    params.put("pFechaFin", fechaFin);
    params.put("pPagina", pagina);
    params.put("pLimite", limite);
    return params;
  }

  private ResultadoSP ejecutarSPConLista(Map<String, Object> params) {
    return ejecutarSPConLista(spCall, params);
  }

  private ResultadoSP construirResultadoSimple(
      Map<String, Object> out,
      String nombreOutSp,
      String nombreFront
  ) {
    Integer respuesta = (Integer) out.get("pRespuesta");
    String mensaje = (String) out.get("pMensaje");

    Map<String, Object> data = new HashMap<>();
    data.put(nombreFront, out.get(nombreOutSp));

    return new ResultadoSP(respuesta, mensaje, data);
  }

  /* =========================
     SP – Operaciones
     ========================= */

  @Override
  public ResultadoSP abrirCaja(Caja caja) {
    Map<String, Object> params = crearParametros(
        1,
        null,
        caja.getSucursal().getIdSucursal(),
        caja.getUsuario().getIdUsuario(),
        caja.getSaldoInicial(),
        null,
        null,
        null,
        null
    );
    return ejecutarSPConLista(params);
  }

  @Override
  public CajaView obtenerUltimaCajaAbiertaUsuario(Integer idUsuario) {
    return jdbcTemplate.query(VW_ULTIMA_CAJA_ABIERTA, new CajaRowMapper(), idUsuario)
        .stream()
        .findFirst()
        .orElse(null);
  }

  @Override
  public ResultadoSP cerrarCaja(Integer idCaja) {
    Map<String, Object> params = crearParametros(
        2, idCaja, null, null,
        null, null, null, null, null
    );
    return ejecutarSPConLista(params);
  }

  @Override
  public ResultadoSP filtrarMisCajasPorRango(
      Integer idUsuario,
      LocalDate fechaInicio,
      LocalDate fechaFin,
      int pagina,
      int limite
  ) {
    Map<String, Object> params = crearParametros(
        3, null, null, idUsuario,
        null, fechaInicio, fechaFin, pagina, limite
    );
    return ejecutarSPConLista(params);
  }

  @Override
  public ResultadoSP filtrarTotalCajasPorRango(
      Integer idUsuario,
      LocalDate fechaInicio,
      LocalDate fechaFin,
      int pagina,
      int limite
  ) {
    Map<String, Object> params = crearParametros(
        4, null, null, idUsuario,
        null, fechaInicio, fechaFin, pagina, limite
    );
    return ejecutarSPConLista(params);
  }

  @Override
  public ResultadoSP obtenerSiguienteCodigoCaja(Integer idSucursal) {
    Map<String, Object> params = crearParametros(
        5, null, idSucursal, null,
        null, null, null, null, null
    );

    Map<String, Object> out = spCall.execute(params);
    return construirResultadoSimple(out, "pCodCaja", "codCaja");
  }

  @Override
  public CajaView obtenerCajaPorId(Integer idCaja) {
    String sql = VW_CAJAS + " WHERE idCaja = ? ";
    return  jdbcTemplate.queryForObject(sql, new CajaRowMapper(), idCaja);
  }

  /* =========================
     Queries directas
     ========================= */

  @Override
  public long contarMisCajas(Integer idUsuario) {
    Long total = jdbcTemplate.queryForObject(COUNT_MIS_CAJAS, Long.class, idUsuario);
    return total != null ? total : 0;
  }

  @Override
  public long contarMisCajasPorRango(
      Integer idUsuario,
      LocalDate fechaInicio,
      LocalDate fechaFin
  ) {
    String sql = COUNT_MIS_CAJAS +
        " AND fechaApertura >= ? AND fechaCierre < DATE_ADD(?, INTERVAL 1 DAY)";
    Long total = jdbcTemplate.queryForObject(sql, Long.class, idUsuario, fechaInicio, fechaFin);
    return total != null ? total : 0;
  }

  @Override
  public long contarTotalCajas() {
    Long total = jdbcTemplate.queryForObject(COUNT_CAJAS, Long.class);
    return total != null ? total : 0;
  }

  @Override
  public long contarTotalCajasPorRango(
      Integer idUsuario,
      LocalDate fechaInicio,
      LocalDate fechaFin
  ) {
    String sql = COUNT_CAJAS +
        " WHERE idUsuario = ? AND fechaApertura >= ? AND fechaCierre < DATE_ADD(?, INTERVAL 1 DAY)";
    Long total = jdbcTemplate.queryForObject(sql, Long.class, idUsuario, fechaInicio, fechaFin);
    return total != null ? total : 0;
  }

  @Override
  public List<CajaView> listarMisCajas(Integer idUsuario, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_MIS_CAJAS + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new CajaRowMapper(), idUsuario, limite, offset);
  }

  @Override
  public List<CajaView> listarTotalCajas(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_CAJAS + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new CajaRowMapper(), limite, offset);
  }

  @Override
  public List<CajaView> mostrarMiUltimaCajaAbierta(Integer idUsuario) {
    return jdbcTemplate.query(
        VW_ULTIMA_CAJA_ABIERTA,
        new CajaRowMapper(),
        idUsuario
    );
  }
}
