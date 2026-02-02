package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.VinosCompraView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VinosCompraViewMapper implements RowMapper<VinosCompraView> {

  @Override
  public VinosCompraView mapRow(ResultSet rs, int rowNum) throws SQLException {
    VinosCompraView view = new VinosCompraView();
    view.setIdVino(rs.getInt("idVino"));
    view.setNombreVino(rs.getString("nombreVino"));
    view.setIdCatalogo(rs.getInt("idCatalogo"));
    view.setPrecioUnidad(rs.getDouble("precioUnidad"));
    view.setIdPresentacion(rs.getInt("idProveedor"));
    view.setRazonSocial(rs.getString("razonSocial"));
    view.setIdCategoria(rs.getInt("idCategoria"));
    view.setCategoria(rs.getString("categoria"));
    view.setIdPresentacion(rs.getInt("idPresentacion"));
    view.setPresentacion(rs.getString("presentacion"));
    view.setTipoVino(rs.getString("tipoVino"));
    view.setOrigenVino(rs.getString("origenVino"));
    view.setEstadoVino(rs.getString("estadoVino"));
    return view;
  }
}
