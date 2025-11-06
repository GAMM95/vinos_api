package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.ProveedorView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProveedorRowMapper implements RowMapper<ProveedorView> {

  @Override
  public ProveedorView mapRow(ResultSet rs, int rowNum) throws SQLException {
    ProveedorView proveedor = new ProveedorView();
    proveedor.setIdProveedor(rs.getInt("idProveedor"));
    proveedor.setCodProveedor(rs.getString("codProveedor"));
    proveedor.setNombre(rs.getString("nombre"));
    proveedor.setContacto(rs.getString("contacto"));
    proveedor.setUbicacion(rs.getString("ubicacion"));
    proveedor.setEstado(rs.getString("estado"));
    return proveedor;
  }
}
