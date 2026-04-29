package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.VentaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VentaRowMapper implements RowMapper<VentaDTO> {
  @Override
  public VentaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    VentaDTO v = new VentaDTO();
    v.setIdVenta(rs.getInt("idVenta"));
    v.setCodVenta(rs.getString("codVenta"));
    v.setIdSucursal(rs.getInt("idSucursal"));
    v.setSucursal(rs.getString("sucursal"));
    v.setFechaVenta(rs.getTimestamp("fechaVenta").toLocalDateTime());
    v.setMetodoPago(rs.getString("metodoPago"));
    v.setDescuento(rs.getBigDecimal("descuento"));
    v.setTotal(rs.getBigDecimal("total"));
    v.setIdUsuario(rs.getInt("idUsuario") );
    v.setUsuario(rs.getString("usuario"));
    v.setUsername(rs.getString("username"));
    v.setEstado(rs.getString("estado"));
    return v;
  }
}
