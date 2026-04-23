package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.ProveedorDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProveedorRowMapper implements RowMapper<ProveedorDTO> {

  @Override
  public ProveedorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    ProveedorDTO proveedor = new ProveedorDTO();
    proveedor.setIdProveedor(rs.getInt("idProveedor"));
    proveedor.setCodProveedor(rs.getString("codProveedor"));
    proveedor.setRazonSocial(rs.getString("razonSocial"));
    proveedor.setRuc(rs.getString("ruc"));
    proveedor.setContacto(rs.getString("contacto"));
    proveedor.setOrigen(rs.getString("origen"));
    proveedor.setUbicacion(rs.getString("ubicacion"));
    proveedor.setEstado(rs.getString("estado"));
    return proveedor;
  }
}
