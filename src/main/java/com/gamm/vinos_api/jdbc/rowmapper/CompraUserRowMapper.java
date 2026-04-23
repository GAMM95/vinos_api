package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.CompraDTO;
import com.gamm.vinos_api.jdbc.rowmapper.utils.CompraMapperUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompraUserRowMapper implements RowMapper<CompraDTO> {

  @Override
  public CompraDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CompraDTO v = new CompraDTO();
    CompraMapperUtil.mapCompraBase(v, rs);
    return v;
  }
}
