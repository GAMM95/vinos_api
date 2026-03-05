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
    cajaView.setCodCaja(rs.getString("codCaja"));
    cajaView.setIdSucursal(rs.getInt("idSucursal"));
    cajaView.setSucursal(rs.getString("sucursal"));
    cajaView.setIdUsuario(rs.getInt("idUsuario"));
    cajaView.setUsuario(rs.getString("usuario"));
    Timestamp fa = rs.getTimestamp("fechaApertura");
    cajaView.setFechaApertura(fa != null ? fa.toLocalDateTime() : null);
    Timestamp fc = rs.getTimestamp("fechaCierre");
    cajaView.setFechaCierre(fc != null ? fc.toLocalDateTime() : null);
    cajaView.setSaldoInicial(rs.getBigDecimal("saldoInicial"));
    cajaView.setSaldoActual(rs.getBigDecimal("saldoActual"));
    cajaView.setSaldoFinal(rs.getBigDecimal("saldoFinal"));
    cajaView.setEstado(rs.getString("estado"));
    return cajaView;
  }
}
