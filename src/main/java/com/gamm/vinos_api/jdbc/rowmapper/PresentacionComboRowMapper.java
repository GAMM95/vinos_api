package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.cbo.PresentacionComboDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PresentacionComboRowMapper implements RowMapper<PresentacionComboDTO> {
  @Override
  public PresentacionComboDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    PresentacionComboDTO presentacionCbo = new PresentacionComboDTO();
    presentacionCbo.setIdPresentacion(rs.getInt("idPresentacion"));
    presentacionCbo.setNombrePresentacion(rs.getString("nombrePresentacion"));
    return presentacionCbo;
  }
}
