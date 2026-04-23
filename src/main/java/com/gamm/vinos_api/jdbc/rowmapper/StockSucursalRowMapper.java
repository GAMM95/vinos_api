package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.StockDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockSucursalRowMapper implements RowMapper<StockDTO> {

  @Override
  public StockDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    StockDTO v = new StockDTO();
    StockMapperUtil.mapStockBase(v, rs);
    StockMapperUtil.mapStockTotal(v, rs);
    return v;
  }
}