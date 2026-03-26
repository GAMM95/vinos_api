package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.DetalleVentaView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleVentaRowMapper implements RowMapper<DetalleVentaView> {
  @Override
  public DetalleVentaView mapRow(ResultSet rs, int rowNum) throws SQLException {
    DetalleVentaView dv = new DetalleVentaView();
    dv.setIdDetalleVenta(rs.getInt("idDetalleVenta"));
    dv.setIdVenta(rs.getInt("idVenta"));
    dv.setNombreVino(rs.getString("nombreVino"));
    dv.setCantidadLitros(rs.getBigDecimal("cantidadLitros"));
    dv.setPrecioLitro(rs.getBigDecimal("precioLitro"));
    dv.setSubtotal(rs.getBigDecimal("subtotal"));
    return dv;
  }
}
