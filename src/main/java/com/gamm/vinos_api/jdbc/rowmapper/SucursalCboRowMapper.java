package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.cbo.SucursalCbo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SucursalCboRowMapper implements RowMapper<SucursalCbo> {

  @Override
  public SucursalCbo mapRow(ResultSet rs, int rowNum) throws SQLException {
    SucursalCbo cbo = new SucursalCbo();
    cbo.setIdSucursal(rs.getInt("idSucursal"));
    cbo.setNombreSucursal(rs.getString("nombreSucursal"));
    return cbo;
  }
}
