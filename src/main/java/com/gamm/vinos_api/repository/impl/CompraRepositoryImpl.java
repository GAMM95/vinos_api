package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.enums.EstadoCompra;
import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.model.DetalleCompra;
import com.gamm.vinos_api.dto.view.CarritoCompraDTO;
import com.gamm.vinos_api.dto.view.CompraDTO;
import com.gamm.vinos_api.dto.view.ProductosCarritoDTO;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.*;
import com.gamm.vinos_api.repository.CompraRepository;
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
public class CompraRepositoryImpl extends SimpleJdbcDAOBase implements CompraRepository {

  private static final String SP_COMPRA = "sp_compra";
  private static final String RESULT_SET_KEY = "#result-set-1";

  private static final String COUNT_PRODUCTS_IN_PURCHASE =
      "SELECT COUNT(*) FROM vw_listarProductosDelCarrito WHERE idCompra = ? AND idUsuario = ?";
  private static final String VW_PRODUCTS_IN_PURCHASE =
      "SELECT * FROM vw_listarProductosDelCarrito WHERE idCompra = ? AND idUsuario = ?";
  private static final String VW_CARRITO_PENDIENTE = "SELECT * FROM vw_listarProductosDelCarrito WHERE idUsuario = ?";

  // Querys para compras de cada usuario
  private static final String COUNT_COMPRAS_USUARIO = "SELECT COUNT(DISTINCT idCompra) FROM vw_compras WHERE idUsuario = ?";
  //  private static final String VW_COMPRAS_USUARIO = "SELECT idCompra, codCompra, usuario, fechaCompra, MAX(subtotalCalculado) AS subtotalCalculado, MAX(costoLogistico) AS costoLogistico, MAX(totalCompra) AS totalCompra, estado FROM vw_compras WHERE idUsuario = ? GROUP BY idCompra, codCompra, usuario, fechaCompra, estado ORDER BY fechaCompra DESC";
  private static final String VW_COMPRAS_USUARIO = "SELECT idCompra, codCompra, usuario, fechaCompra, subtotalCalculado, costoLogistico, totalCompra, estado FROM vw_compras_usuario WHERE idUsuario = ?";
  private static final String VW_DETALLE_COMPRAS_USUARIO = "SELECT * FROM vw_compras WHERE idUsuario = ? AND idCompra = ? ORDER BY idDetCompra";
  private static final String VW_DETALLE_COMPRAS_ADMIN = "SELECT * FROM vw_compras WHERE idCompra = ? ORDER BY idDetCompra";

  // Queries para todas las compras totales
  private static final String COUNT_TOTAL_COMPRAS = "SELECT COUNT(DISTINCT idCompra) FROM vw_compras";
  private static final String VW_COMPRAS_TOTAL = "SELECT * FROM vw_compras ORDER BY fechaCompra DESC ";
  private static final String VW_CARRITO_COMPRAS = "SELECT * FROM vw_carritos_compra";

  // Queries para listar todas las compras confirmadas
  private static final String COUNT_CONFIRM_COMPRAS = "SELECT COUNT(DISTINCT idCompra) FROM vw_compras WHERE estado IN ('Confirmada','Cerrada')";
  private static final String VW_CONFIRM_COMPRAS = "SELECT idCompra, codCompra,idUsuario, usuario, fechaCompra, MAX(subtotalCalculado) AS subtotalCalculado, MAX(costoLogistico) AS costoLogistico, MAX(totalCompra) AS totalCompra, estado , username, rol FROM vw_compras WHERE estado IN ('Confirmada','Cerrada') GROUP BY idCompra, codCompra, usuario, fechaCompra, estado ORDER BY fechaCompra DESC";

  // Queries para listar las compras pendientes
  private static final String VW_PEND_COMPRAS = "SELECT idCompra, codCompra, idUsuario, usuario, username, fechaCarrito, MAX(subtotalCalculado) AS subtotalCalculado, MAX(totalCompra) AS totalCompra, estado , rol FROM vw_comprasPendientes GROUP BY idCompra, codCompra, usuario, username, fechaCarrito, estado ORDER BY fechaCarrito DESC";

  // Queries para listar compras anuladas
  private static final String COUNT_ANULADAS_COMPRAS = "SELECT COUNT(DISTINCT idCompra) FROM vw_compras WHERE estado LIKE 'Anulada'";
  private static final String VW_ANULADAS_COMPRAS = "SELECT idCompra, codCompra, idUsuario, usuario, fechaCompra, MAX(subtotalCalculado) AS subtotalCalculado, MAX(costoLogistico) AS costoLogistico, MAX(totalCompra) AS totalCompra, estado , username, rol FROM vw_compras WHERE estado LIKE 'Anulada' GROUP BY idCompra, codCompra, usuario, fechaCompra, estado ORDER BY fechaCompra DESC";
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
            new SqlParameter("pIdUsuario", Types.INTEGER),
            new SqlParameter("pIdCompra", Types.INTEGER),
            new SqlParameter("pCostoEmbalaje", Types.DECIMAL),
            new SqlParameter("pCostoEnvioAgencia", Types.DECIMAL),
            new SqlParameter("pCostoTransporte", Types.DECIMAL),
            new SqlParameter("pMetodoPago", Types.VARCHAR),
            new SqlParameter("pObservaciones", Types.VARCHAR),
            new SqlParameter("pIdCatalogo", Types.SMALLINT),
            new SqlParameter("pCantidad", Types.TINYINT),
            new SqlParameter("pNombreProveedor", Types.VARCHAR),
            new SqlParameter("pFechaInicio", Types.DATE),
            new SqlParameter("pFechaFin", Types.DATE),
            new SqlOutParameter(PARAM_RESPUESTA, Types.TINYINT),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        );
  }

  /**
   * Metodos base
   */
  private Map<String, Object> baseParams(int tipo, Integer idUsuario) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdUsuario", idUsuario);
    params.put("pIdCompra", null);
    params.put("pCostoEmbalaje", null);
    params.put("pCostoEnvioAgencia", null);
    params.put("pCostoTransporte", null);
    params.put("pMetodoPago", null);
    params.put("pObservaciones", null);
    params.put("pIdCatalogo", null);
    params.put("pCantidad", null);
    params.put("pNombreProveedor", null);
    params.put("pFechaInicio", null);
    params.put("pFechaFin", null);
    return params;
  }

  private ResultadoSP ejecutarSPBasico(int tipo, Integer idUsuario, Compra compra, DetalleCompra detalle) {
    Map<String, Object> params = baseParams(tipo, idUsuario);

    if (compra != null) {
      params.put("pIdCompra", compra.getIdCompra());
      params.put("pCostoEmbalaje", compra.getCostoEmbalaje());
      params.put("pCostoEnvioAgencia", compra.getCostoEnvioAgencia());
      params.put("pCostoTransporte", compra.getCostoTransporte());
      params.put("pMetodoPago", compra.getMetodoPago() != null ? compra.getMetodoPago().name() : null);
      params.put("pObservaciones", compra.getObservaciones());
    }

    if (detalle != null) {
      params.put("pIdCatalogo", detalle.getIdCatalogo());
      params.put("pCantidad", detalle.getCantidad());
    }

    return ejecutarSP(spCall, params);
  }

  private ResultadoSP ejecutarSPFiltro(int tipo, Integer idUsuario, LocalDate fi, LocalDate ff) {
    Map<String, Object> params = baseParams(tipo, idUsuario);
    params.put("pFechaInicio", fi != null ? Date.valueOf(fi) : null);
    params.put("pFechaFin", ff != null ? Date.valueOf(ff) : null);
    return ejecutarSP(spCall, params, RESULT_SET_KEY);
  }

  // ===================== Métodos públicos =====================
  @Override
  public ResultadoSP agregarProductoCarrito(Integer idUsuario, Compra compra, DetalleCompra detalle) {
    return ejecutarSPBasico(TipoSP.AGREGAR.v(), idUsuario, compra, detalle);
  }

  @Override
  public ResultadoSP eliminarProductoCarrito(Integer idUsuario, Integer idDetCompra) {
    Compra c = new Compra();
    c.setIdCompra(idDetCompra);
    return ejecutarSPBasico(TipoSP.ELIMINAR.v(), idUsuario, c, null);
  }

  @Override
  public ResultadoSP actualizarCantidadProductoCarrito(Integer idUsuario, Compra compra, DetalleCompra detalle) {
    return ejecutarSPBasico(TipoSP.ACTUALIZAR.v(), idUsuario, compra, detalle);
  }

  @Override
  public ResultadoSP confirmarCompra(Integer idUsuario, Compra compra) {
    return ejecutarSPBasico(TipoSP.CONFIRMAR.v(), idUsuario, compra, null);
  }

  @Override
  public long contarProductosCarrito(Integer idUsuario, Integer idCompra) {
    if (idCompra == null) {
      Compra carrito = obtenerCarritoPendiente(idUsuario);
      if (carrito == null) return 0;
      idCompra = carrito.getIdCompra();
    }
    Long total = jdbcTemplate.queryForObject(
        COUNT_PRODUCTS_IN_PURCHASE,
        Long.class,
        idCompra,
        idUsuario
    );
    return total != null ? total : 0;
  }

  @Override
  public List<ProductosCarritoDTO> listarProductosCarritos(Integer idUsuario, Integer idCompra) {
    if (idCompra == null) {
      Compra carrito = obtenerCarritoPendiente(idUsuario);
      if (carrito == null) return List.of();
      idCompra = carrito.getIdCompra();
    }

    return jdbcTemplate.query(
        VW_PRODUCTS_IN_PURCHASE,
        new ProductosCarritoRowMapper(),
        idCompra,
        idUsuario
    );
  }

  @Override
  public Compra obtenerCarritoPendiente(Integer idUsuario) {
    List<ProductosCarritoDTO> detalles = jdbcTemplate.query(
        VW_CARRITO_PENDIENTE,
        new ProductosCarritoRowMapper(),
        idUsuario
    );

    if (detalles.isEmpty()) return null;

    Compra carrito = new Compra();
    carrito.setIdCompra(detalles.get(0).getIdCompra());
    carrito.setCodCompra(detalles.get(0).getCodCompra());
    carrito.setEstado(EstadoCompra.PENDIENTE);
    carrito.setIdUsuario(idUsuario);

    carrito.setTotalCompra(
        detalles.stream()
            .map(d -> BigDecimal.valueOf(d.getSubtotal()))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
    );

    carrito.setDetalles(
        detalles.stream().map(d -> {
          DetalleCompra det = new DetalleCompra();
          det.setIdCatalogo(d.getIdCatalogo());
          det.setCantidad(d.getCantidad());
          det.setSubtotal(BigDecimal.valueOf(d.getSubtotal()));
          det.setIdDetalleCompra(d.getIdDetCompra());
          det.setIdCompra(d.getIdCompra());
          return det;
        }).toList()
    );

    return carrito;
  }

  @Override
  public List<CarritoCompraDTO> listarCarritosCompra() {
    return jdbcTemplate.query(VW_CARRITO_COMPRAS, new CarritosCompraRowMapper());
  }

  @Override
  public long contarComprasUsuario(Integer idUsuario) {
    Long total = jdbcTemplate.queryForObject(
        COUNT_COMPRAS_USUARIO,
        Long.class,
        idUsuario
    );
    return total != null ? total : 0;
  }

  @Override
  public List<CompraDTO> listarComprasUsuario(Integer idUsuario, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    return jdbcTemplate.query(
        VW_COMPRAS_USUARIO + " LIMIT ? OFFSET ?",
        new CompraUserRowMapper(),
        idUsuario,
        limite,
        offset
    );
  }

  @Override
  public List<CompraDTO> listarDetalleComprasUsuario(Integer idUsuario, Integer idCompra) {
    return jdbcTemplate.query(
        VW_DETALLE_COMPRAS_USUARIO,
        new DetalleCompraUserRowMapper(),
        idUsuario,
        idCompra
    );
  }

  @Override
  public List<CompraDTO> listarDetalleCompraAdmin(Integer idCompra) {
    return jdbcTemplate.query(
        VW_DETALLE_COMPRAS_ADMIN,
        new DetalleCompraUserRowMapper(),
        idCompra
    );
  }

  @Override
  public ResultadoSP anularCompra(Integer idUsuario, Integer idCompra) {
    Compra c = new Compra();
    c.setIdCompra(idCompra);
    return ejecutarSPBasico(TipoSP.ANULAR.v(), idUsuario, c, null);
  }

  @Override
  public ResultadoSP revertirCompra(Integer idUsuario, Integer idCompra) {
    Compra c = new Compra();
    c.setIdCompra(idCompra);
    return ejecutarSPBasico(TipoSP.REVERTIR.v(), idUsuario, c, null);
  }

  @Override
  public ResultadoSP filtrarMisComprasRangoFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
    return ejecutarSPFiltro(TipoSP.FILTRAR_USUARIO.v(), idUsuario, fechaInicio, fechaFin);
  }

  @Override
  public ResultadoSP cerrarCompra(Integer idCompra) {
    Compra c = new Compra();
    c.setIdCompra(idCompra);
    return ejecutarSPBasico(TipoSP.CERRAR.v(), null, c, null);
  }

  @Override
  public ResultadoSP deshacerCerrarCompra(Integer idCompra) {
    Compra c = new Compra();
    c.setIdCompra(idCompra);
    return ejecutarSPBasico(TipoSP.DESHACER_CERRAR.v(), null, c, null);
  }

  @Override
  public ResultadoSP filtrarComprasUsuarioFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
    return ejecutarSPFiltro(TipoSP.FILTRAR_ADMIN.v(), idUsuario, fechaInicio, fechaFin);
  }

  @Override
  public long contarTotalCompras() {
    Long total = jdbcTemplate.queryForObject(COUNT_TOTAL_COMPRAS, Long.class);
    return total != null ? total : 0L;
  }

  @Override
  public List<CompraDTO> listarTotalCompras(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_COMPRAS_TOTAL + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new CompraRowMapper(), limite, offset);
  }

  @Override
  public long contarComprasConfirmadas() {
    Long total = jdbcTemplate.queryForObject(COUNT_CONFIRM_COMPRAS, Long.class);
    return total != null ? total : 0L;
  }

  @Override
  public List<CompraDTO> listarComprasConfirmadas(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_CONFIRM_COMPRAS + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new CompraRowMapper(), limite, offset);
  }

  @Override
  public List<CompraDTO> listarComprasPendientes() {
    return jdbcTemplate.query(VW_PEND_COMPRAS, new CompraPendienteRowMapper());
  }

  @Override
  public long contarComprasAnuladas() {
    Long total = jdbcTemplate.queryForObject(COUNT_ANULADAS_COMPRAS, Long.class);
    return total != null ? total : 0L;
  }

  @Override
  public List<CompraDTO> listarComprasAnuladas(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_ANULADAS_COMPRAS + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new CompraRowMapper(), limite, offset);
  }

  @Override
  public CompraDTO obtenerCompraPorId(Integer idCompra) {
    String sql = "SELECT * FROM vw_compras WHERE idCompra = ?";
    return jdbcTemplate.queryForObject(sql, new CompraRowMapper(), idCompra);
  }

  /* Enums para el tipo de operacion del sp */
  private enum TipoSP {
    AGREGAR(1),
    ELIMINAR(2),
    ACTUALIZAR(3),
    CONFIRMAR(4),
    ANULAR(5),
    REVERTIR(6),
    FILTRAR_USUARIO(7),
    CERRAR(8),
    DESHACER_CERRAR(9),
    FILTRAR_ADMIN(10);

    private final int v;

    TipoSP(int v) {
      this.v = v;
    }

    public int v() {
      return v;
    }
  }
}
