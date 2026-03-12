package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.StockView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockMapperUtil {

  // Campos base comunes
  public static void mapStockBase (StockView v, ResultSet rs) throws SQLException {
    v.setIdSucursal(rs.getInt("idSucursal"));
    v.setSucursal(rs.getString("sucursal"));
  }

  // Vista: vw_stock_sucursal
  public static void mapStockTotal(StockView v, ResultSet rs) throws SQLException {
    v.setTotalLitros(rs.getBigDecimal("totalLitros"));
  }

  // Vista: vw_stock_sucursal_detallado
  public static void mapStockDetallado(StockView v, ResultSet rs) throws SQLException {
    v.setIdStock(rs.getInt("idStock"));
    v.setIdVino(rs.getInt("idVino"));
    v.setNombreVino(rs.getString("nombreVino"));
    v.setPresentacion(rs.getString("presentacion"));
    v.setOrigen(rs.getString("origen"));
    v.setStockGalones(rs.getBigDecimal("stockGalones"));
    v.setStockLitros(rs.getBigDecimal("stockLitros"));
    v.setTotalLitros(rs.getBigDecimal("totalLitros"));
    v.setEstado(rs.getString("estado"));
  }

  // Vista: vw_stock_venta
  public static void mapStockVenta(StockView v, ResultSet rs) throws SQLException {
    v.setIdVino(rs.getInt("idVino"));
    v.setIdSucursal(rs.getInt("idSucursal"));
    v.setNombreVino(rs.getString("nombreVino"));
    v.setCategoria(rs.getString("categoria"));
    v.setOrigen(rs.getString("origen"));
    v.setStockLitros(rs.getBigDecimal("stockLitros"));
    v.setPrecioVenta(rs.getBigDecimal("precioVenta"));
    v.setEstado(rs.getString("estado"));
  }
}
