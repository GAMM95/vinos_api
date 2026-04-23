package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.PrecioDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrecioStockRowMapper implements RowMapper<PrecioDTO> {
  @Override
  public PrecioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    PrecioDTO v = new PrecioDTO();
    PrecioMapperUtil.mapPrecioStockBase(v, rs);
    return v;
  }
}
