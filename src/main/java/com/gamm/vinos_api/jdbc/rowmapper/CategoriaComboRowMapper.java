package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.cbo.CategoriaComboDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaComboRowMapper implements RowMapper<CategoriaComboDTO> {
  @Override
  public CategoriaComboDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CategoriaComboDTO dto = new CategoriaComboDTO();
    dto.setIdCategoria(rs.getInt("idCategoria"));
    dto.setNombreCategoria(rs.getString("nombreCategoria"));
    return dto;
  }
}
