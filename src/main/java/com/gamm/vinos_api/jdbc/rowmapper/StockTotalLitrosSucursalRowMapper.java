package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.StockLitrosSucursalDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockTotalLitrosSucursalRowMapper implements RowMapper<StockLitrosSucursalDTO> {
  @Override
  public StockLitrosSucursalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    StockLitrosSucursalDTO dto = new StockLitrosSucursalDTO();
    DashboardAdminMapperUtil.mapStockTotalLitrosSucursal(dto, rs);
    return dto;
  }
}
