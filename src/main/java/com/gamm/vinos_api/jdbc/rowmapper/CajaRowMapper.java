package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.CajaView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CajaRowMapper implements RowMapper<CajaView> {
  @Override
  public CajaView mapRow(ResultSet rs, int rowNum) throws SQLException {
    CajaView cajaView = new CajaView();
    cajaView.setIdCaja(rs.getInt("idCaja"));
    cajaView.setIdSucursal(rs.getInt("idSucursal"));
    cajaView.setSucursal(rs.getString("sucursal"));
    cajaView.setIdUsuario(rs.getInt("idUsuario"));
    cajaView.setUsuario(rs.getString("usuario"));
//    cajaView.setFechaApertura(rs.getTimestamp("fechaApertura").toLocalDateTime());
//    cajaView.setFechaCierre(rs.getTimestamp("fechaCierre").toLocalDateTime());
    Timestamp fechaAperturaTs = rs.getTimestamp("fechaApertura");
    cajaView.setFechaApertura(fechaAperturaTs != null ? fechaAperturaTs.toLocalDateTime() : null);

    Timestamp fechaCierreTs = rs.getTimestamp("fechaCierre");
    cajaView.setFechaCierre(fechaCierreTs != null ? fechaCierreTs.toLocalDateTime() : null);
    cajaView.setSaldoInicial(rs.getBigDecimal("saldoInicial"));
    cajaView.setSaldoActual(rs.getBigDecimal("saldoActual"));
    cajaView.setSaldoFinal(rs.getBigDecimal("saldoFinal"));
    cajaView.setEstado(rs.getString("estado"));
    return cajaView;
  }
}
