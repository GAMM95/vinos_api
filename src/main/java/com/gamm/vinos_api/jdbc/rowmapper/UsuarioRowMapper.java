package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import com.gamm.vinos_api.domain.enums.Rol;
import com.gamm.vinos_api.domain.model.Persona;
import com.gamm.vinos_api.domain.model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
  @Override
  public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
    Usuario u = new Usuario();
    u.setIdUsuario(rs.getInt("idUsuario"));

    Persona p = new Persona();
    p.setIdPersona(rs.getInt("idPersona"));
    p.setNombres(rs.getString("nombres"));
    p.setApellidoPaterno(rs.getString("apellidoPaterno"));
    p.setApellidoMaterno(rs.getString("apellidoMaterno"));
    p.setCelular(rs.getString("celular"));
    p.setEmail(rs.getString("email"));
    p.setDomicilio(rs.getString("domicilio"));
    u.setPersona(p);

    u.setUsername(rs.getString("username"));
    u.setRutaFoto(rs.getString("rutaFoto"));
    u.setPassword(rs.getString("password"));

    String rol = rs.getString("rol");
    u.setRol(rol != null ? Rol.valueOf(rol.toUpperCase()) : Rol.VENDEDOR);

    String estado = rs.getString("estado");
    u.setEstado(estado != null ? EstadoRegistro.valueOf(estado.toUpperCase()) : EstadoRegistro.ACTIVO);

    return u;
  }
}
