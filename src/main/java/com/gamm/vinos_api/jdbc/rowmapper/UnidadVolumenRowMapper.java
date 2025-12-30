package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.cbo.UnidadVolumenCbo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UnidadVolumenRowMapper implements RowMapper<UnidadVolumenCbo> {

  @Override
  public UnidadVolumenCbo mapRow(ResultSet rs, int rowNum) throws SQLException {
    UnidadVolumenCbo unidadVolumenCbo = new UnidadVolumenCbo();
    unidadVolumenCbo.setIdUnidadVolumen(rs.getInt("idUnidadVolumen"));
    unidadVolumenCbo.setUnidad(rs.getString("unidad"));
    return unidadVolumenCbo;
  }
}
