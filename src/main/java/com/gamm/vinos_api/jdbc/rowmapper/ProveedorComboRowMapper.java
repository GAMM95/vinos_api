package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.cbo.ProveedorComboDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProveedorComboRowMapper implements RowMapper<ProveedorComboDTO> {
  @Override
  public ProveedorComboDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    ProveedorComboDTO cbo = new ProveedorComboDTO();
    cbo.setIdProveedor(rs.getInt("idProveedor"));
    cbo.setRazonSocial(rs.getString("razonSocial"));
    return cbo;
  }
}
