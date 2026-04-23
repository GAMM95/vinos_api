package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.VinosCompraDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VinosCompraViewMapper implements RowMapper<VinosCompraDTO> {

  @Override
  public VinosCompraDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    VinosCompraDTO view = new VinosCompraDTO();
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
//    view.setIdVino(rs.getInt("idVino"));
//    view.setNombre(rs.getString("nombre"));
//    view.setIdCategoria(rs.getInt("idCategoria"));
//    view.setNombreCategoria(rs.getString("nombreCategoria"));
//    view.setDescripcionVino(rs.getString("descripcionVino"));
//    view.setEstadoVino(rs.getString("estadoVino"));