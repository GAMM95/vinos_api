package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardUserMapperUtil {

  // 🔹 Mapea vw_venta_resumen_user para ver el ingreso de ventas del usuario en el mes actual y el total
  public static void mapIngresoVentasUser(IngresoVentasUserDTO v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setTotalMesActual(rs.getBigDecimal("total_mes_actual"));
    v.setTotalHistorico(rs.getBigDecimal("total_historico"));
  }

  // 🔹 Mapea vw_ventas_total_user
  public static void mapVentasTotal(CantidadVentasUserDTO v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setTotalVentasMesActual(rs.getInt("total_ventas_mes_actual"));
    v.setTotalVentasHistorico(rs.getInt("total_ventas_historico"));
  }

  // 🔹 Mapea vw_ventas_user_anual
  public static void mapIngresoVentasAnualUser(IngresoVentasAnualUserDTO v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setMes(rs.getString("mes")); // formato YYYY-MM-01
    v.setTotalRecaudado(rs.getBigDecimal("total_recaudado"));
  }

  public static void mapInversionComprasAnual(InversionComprasAnualUserDTO v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setMes(rs.getString("mes")); // formato YYYY-MM-01
    v.setTotalRecaudado(rs.getBigDecimal("total_recaudado"));
  }

  public static void mapBalanceNetoMensual(BalanceNetoMensualUserDTO v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setMes(rs.getString("mes"));
    v.setBalanceNeto(rs.getBigDecimal("balance_neto"));
    v.setIngresos(rs.getBigDecimal("ingresos"));
    v.setEgresos(rs.getBigDecimal("egresos"));
    v.setReversos(rs.getBigDecimal("reversos"));
  }

  // 🔹 Mapea vw_ventas_litros_user
  public static void mapVentasLitros(VentasLitrosUserDTO v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setLitrosMesActual(rs.getBigDecimal("litros_mes_actual"));
    v.setLitrosTotales(rs.getBigDecimal("litros_totales"));
  }

  // 🔹 Mapea vw_cant_compras_user_anual
  public static void mapCantidadComprasAnual(CantidadComprasMensualUserDTO c, ResultSet rs) throws SQLException {
    c.setIdUsuario(rs.getInt("idUsuario"));
    c.setMes(rs.getString("mes"));
    c.setCantidadCompras(rs.getInt("cantidad_compras"));
  }

  // 🔹 Mapea vw_cant_ventas_user_anual
  public static void mapCantidadVentasAnual(CantidadVentasMensualUserDTO v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setMes(rs.getString("mes"));
    v.setCantidadVentas(rs.getInt("cantidad_ventas"));
  }

  // 🔹 Mapea vw_stock_dash_user
  public static void mapStockDashboardUser(StockDashboardUserDTO s, ResultSet rs) throws SQLException {
    s.setIdSucursal(rs.getInt("idSucursal"));
    s.setNombreVino(rs.getString("nombre_vino"));
    s.setStockLitros(rs.getBigDecimal("stock_litros"));
    s.setTotalLitros(rs.getBigDecimal("stock_total"));
    s.setEstado(rs.getString("estado"));
   }

   // 🔹 Mapea vw_vino_mas_vendido_user
  public static void mapVinoMasVendido(VinoMasVendidoUserDTO v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setNombreVino(rs.getString("nombre_vino"));
    v.setTotalVendido(rs.getBigDecimal("total_vendido"));
  }
}