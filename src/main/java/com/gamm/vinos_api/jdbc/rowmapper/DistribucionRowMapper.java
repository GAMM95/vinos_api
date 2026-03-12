package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.DistribucionView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DistribucionRowMapper implements RowMapper<DistribucionView> {
  @Override
  public DistribucionView mapRow(ResultSet rs, int rowNum) throws SQLException {
    DistribucionView view = new DistribucionView();
    view.setIdDistribucion(rs.getInt("idDistribucion"));
    view.setIdAlmacen(rs.getInt("idAlmacen"));
    view.setIdSucursal(rs.getInt("idSucursal"));
    view.setSucursal(rs.getString("sucursal"));
    view.setVino(rs.getString("vino"));
    view.setOrigen(rs.getString("origen"));
    view.setPresentacion(rs.getString("presentacion"));
    view.setCantidad(rs.getInt("cantidad"));
    view.setFechaDistribucion(rs.getTimestamp("fechaDistribucion").toLocalDateTime());
    return view;
  }
}
