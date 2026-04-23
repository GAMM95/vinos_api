package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.CatalogoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogoRowMapper implements RowMapper<CatalogoDTO> {

  @Override
  public CatalogoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CatalogoDTO catalogoView = new CatalogoDTO();
    catalogoView.setIdCatalogo(rs.getInt("idCatalogo"));
    catalogoView.setIdProveedor(rs.getInt("idProveedor"));
    catalogoView.setCodProveedor(rs.getString("codProveedor"));
    catalogoView.setRazonSocial(rs.getString("razonSocial"));
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
