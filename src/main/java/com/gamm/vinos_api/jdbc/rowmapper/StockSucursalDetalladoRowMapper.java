package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.StockView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockSucursalDetalladoRowMapper implements RowMapper<StockView> {
  @Override
  public StockView mapRow(ResultSet rs, int rowNum) throws SQLException {
    StockView v = new StockView();
    StockMapperUtil.mapStockBase(v, rs);
    StockMapperUtil.mapStockDetallado(v, rs);
    return v;
  }
}
