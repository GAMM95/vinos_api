package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.PrecioView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrecioStockDetalleRowMapper implements RowMapper<PrecioView> {

  @Override
  public PrecioView mapRow(ResultSet rs, int rowNum) throws SQLException {
    PrecioView view = new PrecioView();
    PrecioMapperUtil.mapPrecioStockBase(view, rs);
    PrecioMapperUtil.mapPrecioDetallado(view, rs);
    return view;
  }
}
