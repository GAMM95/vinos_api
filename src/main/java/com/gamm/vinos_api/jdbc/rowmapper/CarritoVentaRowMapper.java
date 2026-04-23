package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.CarritoVentaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarritoVentaRowMapper implements RowMapper<CarritoVentaDTO> {
  @Override
  public CarritoVentaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CarritoVentaDTO v = new CarritoVentaDTO();
    v.setIdDetalleVenta(rs.getInt("idDetalleVenta"));
    v.setIdVenta(rs.getInt("idVenta"));
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
