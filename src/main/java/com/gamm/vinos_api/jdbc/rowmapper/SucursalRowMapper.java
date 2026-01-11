package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.SucursalView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SucursalRowMapper implements RowMapper<SucursalView> {
  @Override
  public SucursalView mapRow(ResultSet rs, int rowNum) throws SQLException {
    SucursalView view = new SucursalView();
    view.setIdSucursal(rs.getInt("idSucursal"));
    view.setNombreSucursal(rs.getString("nombreSucursal")) ;
    view.setUbicacionSucursal(rs.getString("ubicacionSucursal"));
    view.setLatitudSucursal(rs.getDouble("latitudSucursal"));
    view.setLongitudSucursal(rs.getDouble("longitudSucursal"));
    view.setEstadoSucursal(rs.getString("estadoSucursal"));
    return view;
  }
}
