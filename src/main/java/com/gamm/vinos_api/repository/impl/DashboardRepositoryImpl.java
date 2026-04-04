package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.dto.queries.*;
import com.gamm.vinos_api.jdbc.rowmapper.*;
import com.gamm.vinos_api.repository.DashboardRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

  /* 🔹 Queries user */
  private static final String VW_INGRESO_VENTAS_USER = "SELECT * FROM vw_ingresos_venta_user WHERE idUsuario = ?";
  private static final String VW_CANTIDAD_VENTAS_USER = "SELECT * FROM vw_cantidad_ventas_user WHERE idUsuario = ?";
  private static final String VW_VENTAS_LITROS_USER = "SELECT * FROM vw_ventas_litros_user WHERE idUsuario = ?";
  private static final String VW_INGRESO_VENTAS_ANUAL_USER = "SELECT * FROM vw_ingreso_ventas_anual_user WHERE idUsuario = ? ORDER BY mes";
  private static final String VW_INVERSION_COMPRAS_ANUAL_USER = "SELECT * FROM vw_inversion_compras_anual_user WHERE idUsuario = ? ORDER BY mes";
  private static final String VW_CANTIDAD_COMPRAS_ANUAL_USER = "SELECT * FROM vw_cant_compras_user_anual WHERE idUsuario = ? ORDER BY mes";
  private static final String VW_CANTIDAD_VENTAS_ANUAL_USER = "SELECT * FROM vw_cant_ventas_user_anual WHERE idUsuario = ? ORDER BY mes";
  private static final String VW_STOCK_DASHBOARD_USER = "SELECT * FROM vw_stock_dash_user WHERE idSucursal = ?";
  private static final String VW_VINOS_MAS_VENDIDOS_USER = "SELECT * FROM vw_vino_mas_vendido_user WHERE idUsuario = ?";

  /* 🔹 Queries admin */
  private static final String VW_VENTA_RESUMEN_ADMIN = "SELECT * FROM vw_venta_resumen_admin";
  private static final String VW_VENTAS_TOTAL_ADMIN = "SELECT * FROM vw_ventas_total_admin";
  private static final String VW_VENTAS_LITROS_ADMIN = "SELECT * FROM vw_ventas_litros";
  private static final String VW_STOCK_LITROS_TOTAL_SUCURSAL = "SELECT * FROM vw_stock_total_sucursal";
  private static final String VW_STOCK_VINO_SUCURSAL = "SELECT * FROM vw_stock_vino_sucursal";
  private static final String VW_TOTAL_LITROS_ALMACEN = "SELECT * FROM vw_total_litros_almacen";
  private static final String VW_CANTIDAD_VENTAS_MENSUAL_ADMIN = "SELECT * FROM vw_cant_ventas_mensual";
  private static final String VW_CANTIDAD_COMPRAS_MENSUAL_ADMIN = "SELECT * FROM vw_cant_compras_mensual";
  private static final String VW_BALANCE_DIARIO = "SELECT * FROM vw_balance_diario";
  private final JdbcTemplate jdbcTemplate;

  public DashboardRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  // 🔹 Resumen monetario
  @Override
  public IngresoVentasUserDTO getIngresoVentasUser(Integer idUsuario) {
    return jdbcTemplate.queryForObject(VW_INGRESO_VENTAS_USER, new IngresoVentasUserRowMapper(), idUsuario);
  }

  // 🔹 Conteo de ventas
  @Override
  public CantidadVentasUserDTO getCantidadVentasUser(Integer idUsuario) {
    return jdbcTemplate.queryForObject(VW_CANTIDAD_VENTAS_USER, new CantidadVentasUserRowMapper(), idUsuario);
  }

  // 🔹 Litros vendidos
  @Override
  public VentasLitrosUserDTO getVentasLitros(Integer idUsuario) {
    return jdbcTemplate.queryForObject(VW_VENTAS_LITROS_USER, new VentasLitrosUserRowMapper(), idUsuario);
  }

  // 🔹 Ventas últimos 13 meses
  @Override
  public List<IngresoVentasAnualUserDTO> getIngresoVentasAnualesUser(Integer idUsuario) {
    return jdbcTemplate.query(VW_INGRESO_VENTAS_ANUAL_USER, new IngresoVentasAnualUserRowMapper(), idUsuario);
  }

  @Override
  public List<InversionComprasAnualUserDTO> getInversionComprasAnualesUser(Integer idUsuario) {
    return jdbcTemplate.query(VW_INVERSION_COMPRAS_ANUAL_USER, new InversionComprasAnualUserRowMapper(), idUsuario);
  }

  @Override
  public List<CantidadComprasMensualUserDTO> cantidadComprasAnuales(Integer idUsuario) {
    return jdbcTemplate.query(VW_CANTIDAD_COMPRAS_ANUAL_USER, new CantidadComprasMensualUserRowMapper(), idUsuario);
  }

  @Override
  public List<CantidadVentasMensualUserDTO> cantidadVentasAnuales(Integer idUsuario) {
    return jdbcTemplate.query(VW_CANTIDAD_VENTAS_ANUAL_USER, new CantidadVentasMensualUserRowMapper(), idUsuario);
  }

  @Override
  public List<StockDashboardUserDTO> getStockDashboard(Integer idSucursal) {
    return jdbcTemplate.query(VW_STOCK_DASHBOARD_USER, new StockDashboardUserRowMapper(), idSucursal);
  }

  @Override
  public VinoMasVendidoUserDTO getVinoMasVendido(Integer idUsuario) {
    return jdbcTemplate.queryForObject(VW_VINOS_MAS_VENDIDOS_USER, new VinoMasVendidoUserRowMapper(), idUsuario);
  }

  @Override
  public VentaResumenAdminDTO getVentaResumenAdmin() {
    return jdbcTemplate.queryForObject(VW_VENTA_RESUMEN_ADMIN, new VentaResumenAdminRowMapper());
  }

  @Override
  public VentasTotalAdminDTO getVentasTotalAdmin() {
    return jdbcTemplate.queryForObject(VW_VENTAS_TOTAL_ADMIN, new VentasTotalAdminRowMapper());
  }

  @Override
  public LitrosVendidosAdminDTO getVentasLitrosAdmin() {
    return jdbcTemplate.queryForObject(VW_VENTAS_LITROS_ADMIN, new VentasLitrosAdminRowMapper());
  }

  @Override
  public List<StockLitrosSucursalDTO> getStockLitrosTotalesSucursal() {
    return jdbcTemplate.query(VW_STOCK_LITROS_TOTAL_SUCURSAL, new StockTotalLitrosSucursalRowMapper());
  }

  @Override
  public List<StockVinoSucursalDTO> getStockVinoSucursal() {
    return jdbcTemplate.query(VW_STOCK_VINO_SUCURSAL, new StockVinoSucursalRowMapper());
  }

  @Override
  public StockLitrosAlmacenDTO getTotalLitrosAlmacen() {
    return jdbcTemplate.queryForObject(VW_TOTAL_LITROS_ALMACEN, new TotalLitrosAlmacenRowMapper());
  }

  @Override
  public List<CantidadVentasMensualAdminDTO> getCantidadVentasMensualAdmin() {
    return jdbcTemplate.query(VW_CANTIDAD_VENTAS_MENSUAL_ADMIN, new CantidadVentasMensualAdminRowMapper());
  }

  @Override
  public List<CantidadComprasMensualAdminDTO> getCantidadComprasMensualAdmin() {
    return jdbcTemplate.query(VW_CANTIDAD_COMPRAS_MENSUAL_ADMIN, new CantidadComprasMensualAdminRowMapper());
  }

  @Override
  public List<BalanceNetoDiarioDTO> getBalanceNetoDiario() {
    return jdbcTemplate.query(VW_BALANCE_DIARIO, new BalanceNetoDiarioRowMapper());
  }
}