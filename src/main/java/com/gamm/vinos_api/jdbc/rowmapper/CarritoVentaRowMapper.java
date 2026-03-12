package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.CarritoVentaView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarritoVentaRowMapper implements RowMapper<CarritoVentaView> {
  @Override
  public CarritoVentaView mapRow(ResultSet rs, int rowNum) throws SQLException {
    CarritoVentaView v = new CarritoVentaView();
    v.setIdDetalleVenta(rs.getInt("idDetalleVenta"));
    v.setIdVino(rs.getInt("idVino"));
    v.setNombreVino(rs.getString("nombreVino"));
    v.setOrigen(rs.getString("origen"));
    v.setCantidadLitros(rs.getBigDecimal("cantidadLitros"));
    v.setPrecioLitro(rs.getBigDecimal("precioLitro"));
    v.setSubTotal(rs.getBigDecimal("subTotal"));
    v.setIdUsuario(rs.getInt("idUsuario"));
    v.setUsuario(rs.getString("usuario"));
    return v;
  }
}
