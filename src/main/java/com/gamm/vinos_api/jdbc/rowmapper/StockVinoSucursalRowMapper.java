package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.StockVinoSucursalDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockVinoSucursalRowMapper implements RowMapper<StockVinoSucursalDTO> {
  @Override
  public StockVinoSucursalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      StockVinoSucursalDTO dto = new StockVinoSucursalDTO();
      DashboardAdminMapperUtil.mapStockVinosSucursal(dto, rs);
      return dto;
  }
}
