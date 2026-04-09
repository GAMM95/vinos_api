package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardAdminMapperUtil {

  // 🔹 Mapea vw_venta_resumen_admin
  public static void mapVentaResumenAdmin(VentaResumenAdminDTO v, ResultSet rs) throws SQLException {
    v.setTotalMesActual(rs.getBigDecimal("total_mes_actual"));
    v.setTotalHistorico(rs.getBigDecimal("total_historico"));
  }

  // 🔹 Mapea vw_ventas_total_admin
  public static void mapVentasTotalAdmin(VentasTotalAdminDTO v, ResultSet rs) throws SQLException {
    v.setTotalVentasMesActual(rs.getInt("total_ventas_mes_actual"));
    v.setTotalVentasHistorico(rs.getInt("total_ventas_historico"));
  }

  // 🔹 Mapea vw_ventas_litros_admin
  public static void mapVentasLitrosAdmin(LitrosVendidosAdminDTO v, ResultSet rs) throws SQLException {
    v.setLitrosMesActual(rs.getBigDecimal("litros_mes_actual"));
    v.setLitrosTotales(rs.getBigDecimal("litros_totales"));
  }

  // 🔹 Mapea vw_stock_total_litros_sucursal
  public static void mapStockTotalLitrosSucursal(StockLitrosSucursalDTO s, ResultSet rs) throws SQLException {
    s.setSucursal(rs.getString("sucursal"));
    s.setStockLitros(rs.getBigDecimal("stock_litros"));
  }

  // 🔹 Mapea el stock total de cada vino en litros de cada sucursal
  public static void  mapStockVinosSucursal (StockVinoSucursalDTO s, ResultSet rs) throws SQLException {
    s.setSucursal(rs.getString("sucursal"));
    s.setNombreVino(rs.getString("nombreVino"));
    s.setStockLitros(rs.getBigDecimal("stockLitros"));
  }

  // 🔹 Mapea el total de litros en almacen
  public static void mapTotalLitrosAlmacen(StockLitrosAlmacenDTO t, ResultSet rs) throws SQLException {
    t.setStockLitros(rs.getBigDecimal("totalLitros"));
  }

  // 🔹 Mapea la cantidad total de ventas por cada mes
  public static void mapCantidadVentasMensualAdmin(CantidadVentasMensualAdminDTO c, ResultSet rs) throws SQLException {
    c.setMes(rs.getString("mes"));
    c.setCantidadVentas(rs.getInt("cantidad_ventas"));
  }

  // 🔹 Mapea la cantidad de compras en los últimos 12 meses para el administrador
  public static void mapCantidadComprasMensualAdmin(CantidadComprasMensualAdminDTO c, ResultSet rs) throws SQLException {
    c.setMes(rs.getString("mes"));
    c.setCantidadCompras(rs.getInt("cantidad_compras"));
  }

  // 🔹 Mapea el balance neto diario de compras y ventas
  public static void mapBalanceNetoDiario(BalanceNetoDiarioDTO dto, ResultSet rs) throws SQLException {
    dto.setDia(rs.getString("dia"));
    dto.setVentasNetas(rs.getBigDecimal("ventas_netas"));
    dto.setComprasNetas(rs.getBigDecimal("compras_netas"));
    dto.setBalanceNeto(rs.getBigDecimal("balance_total"));
  }

  // Mapear el balance global
  public static void mapBalanceGlobal(BalanceGlobalDTO dto, ResultSet rs) throws SQLException {
    dto.setBalanceNeto(rs.getBigDecimal("balance_neto"));
    dto.setIngresos(rs.getBigDecimal("ingresos"));
    dto.setEgresos(rs.getBigDecimal("egresos"));
    dto.setReversos(rs.getBigDecimal("reversos"));
  }
}
