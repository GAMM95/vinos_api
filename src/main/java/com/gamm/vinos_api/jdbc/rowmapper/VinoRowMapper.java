package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.VinoView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VinoRowMapper implements RowMapper<VinoView> {
  @Override
  public VinoView mapRow(ResultSet rs, int rowNum) throws SQLException {
    VinoView view = new VinoView();
    view.setIdVino(rs.getInt("idVino"));
    view.setNombreVino(rs.getString("nombreVino"));
    view.setNombreCategoria(rs.getString("nombreCategoria"));
    view.setPrecioVenta(rs.getDouble("precioVenta"));
    return view;
  }
}
