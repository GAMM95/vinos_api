package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.CatalogoView;
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
    catalogoView.setIdVino(rs.getInt("idVino"));
    catalogoView.setNombreVino(rs.getString("nombreVino"));
    catalogoView.setIdPresentacion(rs.getInt("idPresentacion"));
    catalogoView.setNombrePresentacion(rs.getString("nombrePresentacion"));
    catalogoView.setVolumen(rs.getDouble("volumen"));
    catalogoView.setPresentacion(rs.getString("presentacion"));
    catalogoView.setPrecioUnidad(rs.getDouble("precioUnidad"));
    catalogoView.setTipoVino(rs.getString("tipoVino"));
    catalogoView.setEstadoCatalogo(rs.getString("estadoCatalogo"));
    return catalogoView;
  }
}
