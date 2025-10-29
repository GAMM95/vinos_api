package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.CatalogoView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogoRowMapper implements RowMapper<CatalogoView> {

  @Override
  public CatalogoView mapRow(ResultSet rs, int rowNum) throws SQLException {
    CatalogoView catalogoView = new CatalogoView();
    catalogoView.setIdCatalogo(rs.getInt("idCatalogo"));
    catalogoView.setIdProveedor(rs.getInt("idProveedor"));
    catalogoView.setCodProveedor(rs.getString("codProveedor"));
    catalogoView.setNombreProveedor(rs.getString("nombreProveedor"));
    catalogoView.setNombreVino(rs.getString("nombreVino"));
    catalogoView.setNombrePresentacion(rs.getString("nombrePresentacion"));
    catalogoView.setLitrosUnidad(rs.getDouble("litrosUnidad"));
    catalogoView.setPrecioUnidad(rs.getDouble("precioUnidad"));
    return catalogoView;
  }
}
