package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.StockDashboardUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockDashboardUserRowMapper implements RowMapper<StockDashboardUserDTO> {
  @Override
  public StockDashboardUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    StockDashboardUserDTO stock = new StockDashboardUserDTO();
    DashboardUserMapperUtil.mapStockDashboardUser(stock, rs);
    return stock;
  }
}
