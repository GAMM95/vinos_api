package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.InversionComprasAnualUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InversionComprasAnualUserRowMapper implements RowMapper<InversionComprasAnualUserDTO> {
  @Override
  public InversionComprasAnualUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    InversionComprasAnualUserDTO c = new InversionComprasAnualUserDTO();
    DashboardUserMapperUtil.mapInversionComprasAnual(c, rs);
    return c;
  }
}
