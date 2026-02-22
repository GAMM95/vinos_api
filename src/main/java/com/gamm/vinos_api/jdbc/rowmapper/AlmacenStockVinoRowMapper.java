package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.AlmacenView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlmacenStockVinoRowMapper implements RowMapper<AlmacenView> {
  @Override
  public AlmacenView mapRow(ResultSet rs, int rowNum) throws SQLException {
    AlmacenView v = new AlmacenView();
    AlmacenMapperUtil.mapAlmacenBase(v, rs);
    AlmacenMapperUtil.mapPorVino(v, rs);
    return v;
  }
}
