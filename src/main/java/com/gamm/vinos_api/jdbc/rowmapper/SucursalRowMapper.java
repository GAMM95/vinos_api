package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.SucursalDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SucursalRowMapper implements RowMapper<SucursalDTO> {
  @Override
  public SucursalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    SucursalDTO view = new SucursalDTO();
    view.setIdSucursal(rs.getInt("idSucursal"));
    view.setNombreSucursal(rs.getString("nombreSucursal")) ;
    view.setUbicacionSucursal(rs.getString("ubicacionSucursal"));
    view.setLatitudSucursal(rs.getDouble("latitudSucursal"));
    view.setLongitudSucursal(rs.getDouble("longitudSucursal"));
    view.setEstadoSucursal(rs.getString("estadoSucursal"));
    return view;
  }
}
