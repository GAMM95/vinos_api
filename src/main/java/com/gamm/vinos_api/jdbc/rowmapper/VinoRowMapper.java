package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.VinoView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VinoRowMapper implements RowMapper<VinoView> {
  @Override
  public VinoView mapRow(ResultSet rs, int rowNum) throws SQLException {
    VinoView view = new VinoView();
    view.setIdVino(rs.getInt("idVino"));
    view.setNombre(rs.getString("nombre"));
    view.setIdCategoria(rs.getInt("idCategoria"));
    view.setNombreCategoria(rs.getString("nombreCategoria"));
    view.setDescripcionVino(rs.getString("descripcionVino"));
    view.setEstadoVino(rs.getString("estadoVino"));
    return view;
  }
}
