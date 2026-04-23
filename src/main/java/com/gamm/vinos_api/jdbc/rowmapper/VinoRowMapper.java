package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.VinoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VinoRowMapper implements RowMapper<VinoDTO> {
  @Override
  public VinoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    VinoDTO view = new VinoDTO();
    view.setIdVino(rs.getInt("idVino"));
    view.setNombreVino(rs.getString("nombreVino"));
    view.setIdCategoria(rs.getInt("idCategoria"));
    view.setNombreCategoria(rs.getString("nombreCategoria"));
    view.setDescripcionVino(rs.getString("descripcionVino"));
    view.setEstadoVino(rs.getString("estadoVino"));
    return view;
  }
}
