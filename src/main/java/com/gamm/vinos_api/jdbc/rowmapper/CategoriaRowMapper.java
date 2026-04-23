package com.gamm.vinos_api.jdbc.rowmapper;


import com.gamm.vinos_api.dto.view.CategoriaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CategoriaRowMapper implements RowMapper<CategoriaDTO> {
  @Override
  public CategoriaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CategoriaDTO dto = new CategoriaDTO();
    dto.setIdCategoria(rs.getInt("idCategoria"));
    dto.setNombreCategoria(rs.getString("nombreCategoria"));
    dto.setDescripcionCategoria(rs.getString("descripcionCategoria"));
    dto.setEstado(rs.getString("estado"));
    return dto;
  }
}
