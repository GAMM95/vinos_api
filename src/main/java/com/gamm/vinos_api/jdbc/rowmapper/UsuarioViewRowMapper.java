package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.UsuarioView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioViewRowMapper implements RowMapper<UsuarioView> {

  @Override
  public UsuarioView mapRow(ResultSet rs, int rowNum) throws SQLException {
    UsuarioView u = new UsuarioView();

    u.setIdUsuario(rs.getInt("idUsuario"));
    u.setUsername(rs.getString("username"));
    u.setIdPersona(rs.getInt("idPersona"));
    u.setNombres(rs.getString("nombres"));
    u.setApellidoPaterno(rs.getString("apellidoPaterno"));
    u.setApellidoMaterno(rs.getString("apellidoMaterno"));
    u.setNombreCompleto(rs.getString("nombreCompleto"));
    u.setCelular(rs.getString("celular"));
    u.setEmail(rs.getString("email"));
    u.setDomicilio(rs.getString("domicilio"));
    u.setIdSucursal(rs.getInt("idSucursal"));
    u.setNombreSucursal(rs.getString("nombreSucursal"));
    u.setRutaFoto(rs.getString("rutaFoto"));
    u.setRol(rs.getString("rol"));
    u.setEstado(rs.getString("estado"));
    return u;
  }
}