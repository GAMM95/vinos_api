package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.PrecioView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrecioStockRowMapper implements RowMapper<PrecioView> {
  @Override
  public PrecioView mapRow(ResultSet rs, int rowNum) throws SQLException {
    PrecioView v = new PrecioView();
    PrecioMapperUtil.mapPrecioStockBase(v, rs);
    return v;
  }
}
