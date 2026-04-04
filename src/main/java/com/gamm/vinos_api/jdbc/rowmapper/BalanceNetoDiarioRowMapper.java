package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.BalanceNetoDiarioDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceNetoDiarioRowMapper implements RowMapper<BalanceNetoDiarioDTO> {
  @Override
  public BalanceNetoDiarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    BalanceNetoDiarioDTO b = new BalanceNetoDiarioDTO();
    DashboardAdminMapperUtil.mapBalanceNetoDiario(b, rs);
    return b;
  }
}
