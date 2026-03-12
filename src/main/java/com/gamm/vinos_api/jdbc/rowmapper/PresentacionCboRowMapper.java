package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.cbo.PresentacionCbo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PresentacionCboRowMapper implements RowMapper<PresentacionCbo> {
  @Override
  public PresentacionCbo mapRow(ResultSet rs, int rowNum) throws SQLException {
    PresentacionCbo presentacionCbo = new PresentacionCbo();
    presentacionCbo.setIdPresentacion(rs.getInt("idPresentacion"));
    presentacionCbo.setNombrePresentacion(rs.getString("nombrePresentacion"));
    return presentacionCbo;
  }
}
