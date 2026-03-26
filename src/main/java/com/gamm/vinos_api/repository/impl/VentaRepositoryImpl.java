package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaView;
import com.gamm.vinos_api.dto.view.DetalleVentaView;
import com.gamm.vinos_api.dto.view.VentaView;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CarritoVentaRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.DetalleVentaRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.VentaRowMapper;
import com.gamm.vinos_api.repository.VentaRepository;
import com.gamm.vinos_api.util.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VentaRepositoryImpl extends SimpleJdbcDAOBase implements VentaRepository {

  // Procedimiento almacenado
  private static final String SP_VENTA = "sp_venta";
  // Query para listar el carrito de ventas para cada usuario
  private static final String VW_CARRITO_VENTAS = "SELECT * FROM vw_carrito_venta";

  private static final String COUNT_VENTAS_USUARIO = "SELECT COUNT(DISTINCT idVenta) FROM vw_ventas WHERE idUsuario = ?";

  private static final String COUNT_TOTAL_VENTAS = "SELECT COUNT(*) FROM vw_ventas";

  private static final String VW_VENTAS = "SELECT * FROM vw_ventas";

  // Query para listar el detalle de venta
  private static final String VW_DETALLE_VENTAS = "SELECT * FROM vw_detalleVenta WHERE idVenta = ?";

  private SimpleJdbcCall spCall;
  private SimpleJdbcCall spCallSinLista;

  public VentaRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_VENTA).
        declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdUsuario", Types.INTEGER),
            new SqlParameter("pIdVenta", Types.INTEGER),
            new SqlParameter("pIdSucursal", Types.TINYINT),
            new SqlParameter("pIdVino", Types.TINYINT),
            new SqlParameter("pMetodoPago", Types.VARCHAR),
            new SqlParameter("pCantidad", Types.DECIMAL),
            new SqlParameter("pDescuento", Types.DECIMAL),
            new SqlParameter("pFechaInicio", Types.DATE),
            new SqlParameter("pFechaFin", Types.DATE),
            new SqlParameter("pPagina", Types.INTEGER),
            new SqlParameter("pLimite", Types.INTEGER),
            new SqlOutParameter(PARAM_RESPUESTA, Types.TINYINT),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        )
        .returningResultSet("ResultSet", new VentaRowMapper());


    spCallSinLista = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_VENTA)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdUsuario", Types.INTEGER),
            new SqlParameter("pIdVenta", Types.INTEGER),
            new SqlParameter("pIdSucursal", Types.TINYINT),
            new SqlParameter("pIdVino", Types.TINYINT),
            new SqlParameter("pMetodoPago", Types.VARCHAR),
            new SqlParameter("pCantidad", Types.DECIMAL),
            new SqlParameter("pDescuento", Types.DECIMAL),
            new SqlParameter("pFechaInicio", Types.DATE),
            new SqlParameter("pFechaFin", Types.DATE),
            new SqlParameter("pPagina", Types.INTEGER),
            new SqlParameter("pLimite", Types.INTEGER),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        );
  }

  /* Helpers */
  private Map<String, Object> construirParametros(
      Integer tipo,
      Integer idUsuario,
      Integer idVenta,
      Integer idSucursal,
      Integer idVino,
      String metodoPago,
      BigDecimal cantidad,
      BigDecimal descuento,
      Date fechaInicio,
      Date fechaFin,
      Integer pagina,
      Integer limite
  ) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdUsuario", idUsuario);
    params.put("pIdVenta", idVenta);
    params.put("pIdSucursal", idSucursal);
    params.put("pIdVino", idVino);
    params.put("pMetodoPago", metodoPago);
    params.put("pCantidad", cantidad);
    params.put("pDescuento", descuento);
    params.put("pFechaInicio", fechaInicio);
    params.put("pFechaFin", fechaFin);
    params.put("pPagina", pagina);
    params.put("pLimite", limite);
    return params;
  }

  @Override
  public long contarVentasUsuario(Integer idUsuario) {
    Long total = jdbcTemplate.queryForObject(
        COUNT_VENTAS_USUARIO,
        Long.class,
        idUsuario
    );
    return total != null ? total : 0;
  }

  @Override
  public List<VentaView> listarVentasUsuario(Integer idUsuario, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_VENTAS + " WHERE idUsuario = ? LIMIT ? OFFSET ? ";
    return jdbcTemplate.query(
        sql, new VentaRowMapper(), idUsuario, limite, offset);
  }

  @Override
  public long contarTotalVentas() {
    Long total = jdbcTemplate.queryForObject(COUNT_TOTAL_VENTAS, Long.class);
    return total != null ? total : 0L;
  }

  @Override
  public List<VentaView> listarTotalVentas(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_VENTAS + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new VentaRowMapper(), limite, offset);
  }

  @Override
  public List<DetalleVentaView> listarDetalleVenta(Integer idVenta) {
    return jdbcTemplate.query(VW_DETALLE_VENTAS, new DetalleVentaRowMapper(), idVenta);
  }

  @Override
  public List<CarritoVentaView> listarCarritoVentaUsuario(Integer idUsuario) {
    String sql = VW_CARRITO_VENTAS + " WHERE idUsuario = ? AND estado = 'Pendiente'";
    return jdbcTemplate.query(sql, new CarritoVentaRowMapper(), idUsuario);
  }

  @Override
  public List<CarritoVentaView> listarCarritoVentaAdmin(Integer idVenta) {
    String sql = VW_CARRITO_VENTAS + " WHERE idVenta = ? ";
    return jdbcTemplate.query(sql, new CarritoVentaRowMapper(), idVenta);
  }

  @Override
  public ResultadoSP agregarProductoCarrito(Integer idUsuario, Venta venta, DetalleVenta detalle) {
    Map<String, Object> params = construirParametros(
        1,
        idUsuario,
        null,
        venta.getSucursal().getIdSucursal(),
        detalle.getIdVino(),
        null,
        detalle.getCantidadLitros(),
        null,
        null,
        null,
        null,
        null
    );
    return ejecutarSP(spCall, params);
  }

  @Override
  public ResultadoSP confirmarVenta(Integer idUsuario, Integer idVenta, String metodoPago, BigDecimal descuento) {
    Map<String, Object> params = construirParametros(
        5,
        idUsuario,
        idVenta,
        null,
        null,
        metodoPago,
        null,
        descuento,
        null,
        null,
        null,
        null
    );
    return ejecutarSP(spCallSinLista, params);
  }

  @Override
  public ResultadoSP retirarProductoCarrito(Integer idUsuario, Integer idVenta, Integer idVino) {
    Map<String, Object> params = construirParametros(
        3,
        idUsuario,
        idVenta,
        null,
        idVino,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );
    return ejecutarSP(spCall, params);
  }

  @Override
  public ResultadoSP cancelarVenta(Integer idVenta) {
    Map<String, Object> params = construirParametros(
        6,
        null,
        idVenta,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );
    return ejecutarSP(spCall, params);
  }

  @Override
  public long contarVentasPorFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
    String sql = COUNT_VENTAS_USUARIO + " AND fechaVenta >= ? AND fechaVenta < DATE_ADD(?, INTERVAL 1 DAY) ";
    Long total = jdbcTemplate.queryForObject(sql, Long.class, idUsuario, Date.valueOf(fechaInicio), Date.valueOf(fechaFin));
    return total != null ? total : 0;
  }

  @Override
  public ResultadoSP filtrarMisVentasPorFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Map<String, Object> params = construirParametros(
        7,
        idUsuario,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        pagina,
        limite
        );
    addDateRange(params, "pFechaInicio", "pFechaFin", fechaInicio, fechaFin);
    return ejecutarSPConLista(spCall, params);
  }

  @Override
  public long contarVentasPorUsuarioOFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
    String sql = COUNT_TOTAL_VENTAS + " WHERE idUsuario = ? AND fechaVenta >= ? AND fechaVenta < DATE_ADD(?, INTERVAL 1 DAY) ";
    Long total = jdbcTemplate.queryForObject(sql, Long.class, idUsuario, fechaInicio, fechaFin);
    return total != null ? total : 0;
  }

  @Override
  public ResultadoSP filtrarVentasPorUsuarioOFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Map<String, Object> params = construirParametros(
        8,
        idUsuario,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        pagina,
        limite
    );
    addDateRange(params, "pFechaInicio", "pFechaFin", fechaInicio, fechaFin);
    return ejecutarSPConLista(spCall, params);
  }

}
