package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.MovimientosDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovimientosRowMapper implements RowMapper<MovimientosDTO> {
  @Override
  public MovimientosDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    MovimientosDTO view = new MovimientosDTO();
    view.setIdMovimiento(rs.getInt("idMovimiento"));
    view.setIdUsuario(rs.getInt("idUsuario"));
    view.setUsuario(rs.getString("usuario"));
    view.setIdCaja(rs.getInt("idCaja"));
    view.setCodCaja(rs.getString("codCaja"));
    view.setFechaMovimiento(rs.getTimestamp("fechaMovimiento").toLocalDateTime());
    view.setTipo(rs.getString("tipo"));
    view.setMetodoPago(rs.getString("metodoPago"));
    view.setMonto(rs.getBigDecimal("monto"));
    view.setConcepto(rs.getString("concepto"));
    return view;
  }
}
