package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.cbo.PresentacionChk;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PresentacionChkRowMapper implements RowMapper<PresentacionChk> {

  @Override
  public PresentacionChk mapRow(ResultSet rs, int rowNum) throws SQLException {
    PresentacionChk chk = new PresentacionChk();
    chk.setIdPresentacion(rs.getInt("idPresentacion"));
    chk.setDescripcion(rs.getString("descripcion"));
    return chk;
  }
}
