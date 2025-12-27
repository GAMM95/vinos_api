package com.gamm.vinos_api.jdbc.rowmapper;


import com.gamm.vinos_api.domain.view.PresentacionView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PresentacionRowMapper implements RowMapper<PresentacionView> {
  @Override
  public PresentacionView mapRow(ResultSet rs, int rowNum) throws SQLException {
    PresentacionView view = new PresentacionView();
    view.setIdPresentacion(rs.getInt("idPresentacion"));
    view.setDescripcion(rs.getString("descripcion"));
    view.setVolumen(rs.getDouble("volumen"));
    view.setVolumenTexto(rs.getString("volumenTexto"));
    view.setIdUnidadVolumen(rs.getInt("idUnidadVolumen"));
    view.setUnidad(rs.getString("unidad"));
    view.setEstado(rs.getString("estado"));
    return view;
  }
}
