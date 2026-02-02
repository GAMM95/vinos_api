package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.enums.EstadoCompra;
import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.model.DetalleCompra;
import com.gamm.vinos_api.domain.view.ProductosCarritoView;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.ProductosCarritoRowMapper;
import com.gamm.vinos_api.repository.CompraRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CompraRepositoryImpl extends SimpleJdbcDAOBase implements CompraRepository {

  private static final String SP_COMPRA = "sp_compra";
  private static final String COUNT_PRODUCTS_IN_PURCHASE =
      "SELECT COUNT(*) FROM vw_listarProductosDelCarrito WHERE idCompra = ? AND idUsuario = ?";
  private static final String VW_PRODUCTS_IN_PURCHASE =
      "SELECT * FROM vw_listarProductosDelCarrito WHERE idCompra = ? AND idUsuario = ?";
  private static final String VW_CARRITO_PENDIENTE = "SELECT * FROM vw_listarProductosDelCarrito WHERE idUsuario = ?";

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
            new SqlParameter("pObservaciones", Types.VARCHAR),
            new SqlParameter("pIdCatalogo", Types.SMALLINT),
            new SqlParameter("pCantidad", Types.TINYINT),
            new SqlParameter("pNombreProveedor", Types.VARCHAR),
            new SqlParameter("pFechaInicio", Types.DATE),
            new SqlParameter("pFechaFin", Types.DATE),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        );
  }

  // Ejecuta el SP para agregar, actualizar o crear
  private ResultadoSP ejecutarSP(int tipo, Integer idUsuario, Compra compra, DetalleCompra detalle) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdUsuario", idUsuario);
    params.put("pIdCompra", compra != null ? compra.getIdCompra() : null);
    params.put("pCostoEmbalaje", compra != null ? compra.getCostoEmbalaje() : null);
    params.put("pCostoEnvioAgencia", compra != null ? compra.getCostoEnvioAgencia() : null);
    params.put("pCostoTransporte", compra != null ? compra.getCostoTransporte() : null);
    params.put("pObservaciones", compra != null ? compra.getObservaciones() : null);

    params.put("pIdCatalogo", detalle != null ? detalle.getIdCatalogo() : null);
    params.put("pCantidad", detalle != null ? detalle.getCantidad() : null);

    params.put("pNombreProveedor", null);
    params.put("pFechaInicio", null);
    params.put("pFechaFin", null);

    return ejecutarSP(spCall, params);
  }

  // Ejecuta el SP para eliminar producto
  private ResultadoSP ejecutarSPEliminar(Integer idUsuario, Integer idDetCompra) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", 2);
    params.put("pIdUsuario", idUsuario);
    params.put("pIdCompra", idDetCompra);

    params.put("pCostoEmbalaje", null);
    params.put("pCostoEnvioAgencia", null);
    params.put("pCostoTransporte", null);
    params.put("pObservaciones", null);
    params.put("pIdCatalogo", null);
    params.put("pCantidad", null);
    params.put("pNombreProveedor", null);
    params.put("pFechaInicio", null);
    params.put("pFechaFin", null);

    return ejecutarSP(spCall, params);
  }

  // ===================== Métodos públicos =====================
  @Override
  public ResultadoSP agregarProductoCarrito(Integer idUsuario, Compra compra, DetalleCompra detalle) {
    return ejecutarSP(1, idUsuario, compra, detalle);
  }

  @Override
  public ResultadoSP eliminarProductoCarrito(Integer idUsuario, Integer idDetCompra) {
    return ejecutarSPEliminar(idUsuario, idDetCompra);
  }

  @Override
  public ResultadoSP actualizarCantidadProductoCarrito(Integer idUsuario, Compra compra, DetalleCompra detalle) {
    return ejecutarSP(3, idUsuario, compra, detalle);
  }

  @Override
  public ResultadoSP crearCompra(Integer idUsuario, Compra compra) {
    return ejecutarSP(4, idUsuario, compra, null);
  }

  @Override
  public long contarProductosCarrito(Integer idUsuario, Integer idCompra) {
    if (idCompra == null) {
      Compra carrito = obtenerCarritoPendiente(idUsuario);
      if (carrito == null) return 0L;
      idCompra = carrito.getIdCompra();
    }

    Long total = jdbcTemplate.queryForObject(
        COUNT_PRODUCTS_IN_PURCHASE,
        new Object[]{idCompra, idUsuario},
        Long.class
    );
    return total != null ? total : 0L;
  }

  @Override
  public List<ProductosCarritoView> listarProductosCarritos(Integer idUsuario, Integer idCompra) {
    if (idCompra == null) {
      Compra carrito = obtenerCarritoPendiente(idUsuario);
      if (carrito == null) return List.of();
      idCompra = carrito.getIdCompra();
    }

    return jdbcTemplate.query(
        VW_PRODUCTS_IN_PURCHASE,
        new Object[]{idCompra, idUsuario},
        new ProductosCarritoRowMapper()
    );
  }

  @Override
  public Compra obtenerCarritoPendiente(Integer idUsuario) {
    List<ProductosCarritoView> detalles = jdbcTemplate.query(
        VW_CARRITO_PENDIENTE,
        new Object[]{idUsuario},
        new ProductosCarritoRowMapper()
    );

    if (detalles.isEmpty()) return null;

    Compra carrito = new Compra();
    carrito.setIdCompra(detalles.get(0).getIdCompra());
    carrito.setCodCompra(detalles.get(0).getCodCompra());
    carrito.setTotalCompra(detalles.stream()
        .map(d -> BigDecimal.valueOf(d.getSubtotal()))
        .reduce(BigDecimal.ZERO, BigDecimal::add));
    carrito.setEstado(EstadoCompra.PENDIENTE);
    carrito.setIdUsuario(idUsuario);
    carrito.setDetalles(detalles.stream().map(d -> {
      DetalleCompra detalle = new DetalleCompra();
      detalle.setIdCatalogo(d.getIdCatalogo());
      detalle.setCantidad(d.getCantidad());
      detalle.setSubtotal(BigDecimal.valueOf(d.getSubtotal()));
      detalle.setIdDetalleCompra(d.getIdDetCompra());
      detalle.setIdCompra(d.getIdCompra());
      return detalle;
    }).toList());

    return carrito;
  }
}
