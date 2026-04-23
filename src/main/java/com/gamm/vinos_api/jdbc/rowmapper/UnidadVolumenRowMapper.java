package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.cbo.UnidadVolumenComboDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UnidadVolumenRowMapper implements RowMapper<UnidadVolumenComboDTO> {

  @Override
  public UnidadVolumenComboDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    UnidadVolumenComboDTO unidadVolumenCbo = new UnidadVolumenComboDTO();
    unidadVolumenCbo.setIdUnidadVolumen(rs.getInt("idUnidadVolumen"));
    unidadVolumenCbo.setUnidad(rs.getString("unidad"));
    return unidadVolumenCbo;
  }
}
