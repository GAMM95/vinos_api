package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.BalanceGlobalDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceGlobalRowMapper implements RowMapper<BalanceGlobalDTO> {
  @Override
  public BalanceGlobalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    BalanceGlobalDTO dto = new BalanceGlobalDTO();
    DashboardAdminMapperUtil.mapBalanceGlobal(dto, rs);
    return dto;
  }
}
