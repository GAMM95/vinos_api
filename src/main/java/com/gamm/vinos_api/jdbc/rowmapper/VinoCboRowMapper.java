package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.cbo.VinoCbo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VinoCboRowMapper implements RowMapper<VinoCbo> {

  @Override
  public VinoCbo mapRow(ResultSet rs, int rowNum) throws SQLException {
    VinoCbo vinoCbo = new VinoCbo();
    vinoCbo.setIdVino(rs.getInt("idVino"));
    vinoCbo.setNombreVino(rs.getString("nombreVino"));
    return vinoCbo;
  }
}
