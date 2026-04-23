package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.DetalleVentaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleVentaRowMapper implements RowMapper<DetalleVentaDTO> {
  @Override
  public DetalleVentaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    DetalleVentaDTO dv = new DetalleVentaDTO();
    dv.setIdDetalleVenta(rs.getInt("idDetalleVenta"));
    dv.setIdVenta(rs.getInt("idVenta"));
    dv.setNombreVino(rs.getString("nombreVino"));
    dv.setCantidadLitros(rs.getBigDecimal("cantidadLitros"));
    dv.setPrecioLitro(rs.getBigDecimal("precioLitro"));
    dv.setSubtotal(rs.getBigDecimal("subtotal"));
    return dv;
  }
}
