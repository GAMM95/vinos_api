package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.StockLitrosAlmacenDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalLitrosAlmacenRowMapper implements RowMapper<StockLitrosAlmacenDTO> {
  @Override
  public StockLitrosAlmacenDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    StockLitrosAlmacenDTO dto = new StockLitrosAlmacenDTO();
    DashboardAdminMapperUtil.mapTotalLitrosAlmacen(dto, rs);
    return dto;
  }
}
