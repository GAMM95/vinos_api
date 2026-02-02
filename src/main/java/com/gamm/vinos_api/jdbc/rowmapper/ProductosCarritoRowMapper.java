package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.ProductosCarritoView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductosCarritoRowMapper implements RowMapper<ProductosCarritoView> {
  @Override
  public ProductosCarritoView mapRow(ResultSet rs, int rowNum) throws SQLException {
    ProductosCarritoView view = new ProductosCarritoView();
    view.setIdDetCompra(rs.getInt("idDetCompra"));
    view.setIdCompra(rs.getInt("idCompra"));
    view.setCodCompra(rs.getString("codCompra"));
    view.setIdCatalogo(rs.getInt("idCatalogo"));
    view.setRazonSocial(rs.getString("razonSocial"));
    view.setNombreVino(rs.getString("nombreVino"));
    view.setCategoria(rs.getString("categoria"));
    view.setPresentacion(rs.getString("presentacion"));
    view.setPrecioUnidad(rs.getDouble("precioUnidad"));
    view.setCantidad(rs.getInt("cantidad"));
    view.setSubtotal(rs.getDouble("subtotal"));
    return view;
  }
}
