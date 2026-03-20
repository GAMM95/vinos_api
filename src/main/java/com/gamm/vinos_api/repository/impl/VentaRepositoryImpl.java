package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaView;
import com.gamm.vinos_api.dto.view.CompraView;
import com.gamm.vinos_api.dto.view.VentaView;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CarritoVentaRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.CompraUserRowMapper;
import com.gamm.vinos_api.repository.VentaRepository;
import com.gamm.vinos_api.util.ResultadoSP;
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
public class VentaRepositoryImpl extends SimpleJdbcDAOBase implements VentaRepository {

  // Procedimiento almacenado
  private static final String SP_VENTA = "sp_venta";
  // Query para listar el carrito de ventas para cada usuario
  private static final String VW_CARRITO_VENTAS = "SELECT * FROM vw_carrito_venta";

  private static final String COUNT_VENTAS_USUARIO = "SELECT COUNT(DISTINCT idVenta) FROM vw_ventas WHERE idUsuario = ?";

  private static final String VW_VENTAS = "SELECT * FROM vw_ventas";

  private SimpleJdbcCall spCall;

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
            new SqlOutParameter(PARAM_RESPUESTA, Types.TINYINT),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        );
  }

  /* Helpers */
  private Map<String, Object> construirParametros(
      int tipo,
      Integer idUsuario,
      Integer idVenta,
      Integer idSucursal,
      Integer idVino,
      String metodoPago,
      BigDecimal cantidad
  ) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdUsuario", idUsuario);
    params.put("pIdVenta", idVenta);
    params.put("pIdSucursal", idSucursal);
    params.put("pIdVino", idVino);
    params.put("pMetodoPago", metodoPago);
    params.put("pCantidad", cantidad);
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

//  @Override
//  public List<VentaView> listarVentasUsuario(Integer idUsuario, int pagina, int limite){
//    int offset = (pagina - 1) * limite;
//    return jdbcTemplate.query(
//        VW_VENTAS + " LIMIT ? OFFSET ?",
//        new CompraUserRowMapper(),
//        idUsuario,
//        limite,
//        offset
//    );
//  }

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
        detalle.getCantidadLitros());
    return ejecutarSP(spCall, params);
  }

  @Override
  public ResultadoSP confirmarVenta(Integer idUsuario, Integer idVenta, String metodoPago) {
    Map<String, Object> params = construirParametros(
        5,
        idUsuario,
        idVenta,
        null,
        null,
        metodoPago,
        null
    );
    return ejecutarSP(spCall, params);
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
        null
    );
    return ejecutarSP(spCall, params);
  }
}
