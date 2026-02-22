package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.cbo.UsuarioCbo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioCboRowMapper implements RowMapper<UsuarioCbo> {

  @Override
  public UsuarioCbo mapRow(ResultSet rs, int rowNum) throws SQLException {
    UsuarioCbo usuarioCbo = new UsuarioCbo();
    usuarioCbo.setIdUsuario(rs.getInt("idUsuario"));
    usuarioCbo.setUsuario(rs.getString("usuario"));
    return usuarioCbo;
  }
}
