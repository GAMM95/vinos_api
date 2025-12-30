package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.cbo.ProveedorCbo;
import com.gamm.vinos_api.domain.view.ProveedorView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProveedorCboRowMapper implements RowMapper<ProveedorCbo> {
  @Override
  public ProveedorCbo mapRow(ResultSet rs, int rowNum) throws SQLException {
    ProveedorCbo proveedorCbo = new ProveedorCbo();
    proveedorCbo.setIdProveedor(rs.getInt("idProveedor"));
    proveedorCbo.setRazonSocial(rs.getString("razonSocial"));
    return proveedorCbo;
  }
}
