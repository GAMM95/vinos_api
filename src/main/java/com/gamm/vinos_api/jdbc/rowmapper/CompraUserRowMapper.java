package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.CompraView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraUserRowMapper implements RowMapper<CompraView> {

  @Override
  public CompraView mapRow(ResultSet rs, int rowNum) throws SQLException {
    CompraView v = new CompraView();
    CompraMapperUtil.mapCompraBase(v, rs);
    return v;
  }
}
