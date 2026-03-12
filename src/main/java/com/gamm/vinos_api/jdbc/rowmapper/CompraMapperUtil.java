package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.CarritoCompraView;
import com.gamm.vinos_api.dto.view.CompraView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraMapperUtil {
  // Campos base para CompraView
  public static void mapCompraBase(CompraView v, ResultSet rs) throws SQLException {
    v.setIdCompra(rs.getInt("idCompra"));
    v.setCodCompra(rs.getString("codCompra"));
    v.setFechaCompra(rs.getTimestamp("fechaCompra").toLocalDateTime());
    v.setEstado(rs.getString("estado"));
    v.setSubtotalCalculado(rs.getBigDecimal("subtotalCalculado"));
    v.setCostoLogistico(rs.getBigDecimal("costoLogistico"));
    v.setTotalCompra(rs.getBigDecimal("totalCompra"));
  }

  public static void mapUsuario(CompraView v, ResultSet rs) throws SQLException {
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setUsuario(rs.getString("usuario"));
  }

  public static void mapDetalleCompra(CompraView v, ResultSet rs) throws SQLException {
    v.setIdDetCompra(rs.getInt("idDetCompra"));
    v.setNombreVino(rs.getString("nombreVino"));
    v.setPresentacion(rs.getString("presentacion"));
    v.setCantidad(rs.getInt("cantidad"));
    v.setSubtotal(rs.getBigDecimal("subtotal"));
    v.setCostoTransporte(rs.getBigDecimal("costoTransporte"));
    v.setCostoEmbalaje(rs.getBigDecimal("costoEmbalaje"));
    v.setCostoEnvioAgencia(rs.getBigDecimal("costoEnvioAgencia"));
  }

  public static void mapCompraPendiente(CompraView v, ResultSet rs) throws SQLException {
    v.setIdCompra(rs.getInt("idCompra"));
    v.setCodCompra(rs.getString("codCompra"));
    v.setFechaCarrito(rs.getTimestamp("fechaCarrito").toLocalDateTime()); // diferente campo
    v.setEstado(rs.getString("estado"));
    v.setSubtotalCalculado(rs.getBigDecimal("subtotalCalculado"));
    v.setTotalCompra(rs.getBigDecimal("totalCompra"));
  }
  // Campos base para CarritoCompraView
  public static void mapCarritoBase(CarritoCompraView v, ResultSet rs) throws SQLException {
    v.setIdCompra(rs.getInt("idCompra"));
    v.setCodCompra(rs.getString("codCompra"));
    v.setFechaCarrito(rs.getTimestamp("fechaCarrito").toLocalDateTime());
    v.setEstado(rs.getString("estado"));
    v.setIdDetCompra(rs.getInt("idDetCompra"));
    v.setNombreVino(rs.getString("nombreVino"));
    v.setPresentacion(rs.getString("presentacion"));
    v.setCantidad(rs.getInt("cantidad"));
    v.setSubtotal(rs.getBigDecimal("subtotal"));
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setUsuario(rs.getString("usuario"));
  }
}
