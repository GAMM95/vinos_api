package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.CarritoCompraView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarritosCompraRowMapper implements RowMapper<CarritoCompraView> {
  @Override
  public CarritoCompraView mapRow(ResultSet rs, int rowNum) throws SQLException {
    CarritoCompraView v = new CarritoCompraView();
    CompraMapperUtil.mapCarritoBase(v, rs);
    return v;
  }
}
