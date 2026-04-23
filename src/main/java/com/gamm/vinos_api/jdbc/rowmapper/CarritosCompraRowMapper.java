package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.CarritoCompraDTO;
import com.gamm.vinos_api.jdbc.rowmapper.utils.CompraMapperUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarritosCompraRowMapper implements RowMapper<CarritoCompraDTO> {
  @Override
  public CarritoCompraDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CarritoCompraDTO v = new CarritoCompraDTO();
    CompraMapperUtil.mapCarritoBase(v, rs);
    return v;
  }
}
