package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.CompraView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraRowMapper implements RowMapper<CompraView> {

  @Override
  public CompraView mapRow(ResultSet rs, int rowNum) throws SQLException {
    CompraView view = new CompraView();
    view.setIdCompra(rs.getInt("idCompra"));
    view.setCodCompra(rs.getString("codCompra"));
    view.setFecha(rs.getDate("fecha").toLocalDate());
    view.setEstado(rs.getString("estado"));
    view.setIdDetCompra(rs.getInt("idDetCompra"));
    view.setNombreVino(rs.getString("nombreVino"));
    view.setPresentacion(rs.getString("presentacion"));
    view.setCantidadGalones(rs.getDouble("cantidadGalones"));
    view.setPrecioUnidad(rs.getDouble("precioUnidad"));
    view.setSubtotal(rs.getDouble("subtotal"));
    return view;
  }
}
