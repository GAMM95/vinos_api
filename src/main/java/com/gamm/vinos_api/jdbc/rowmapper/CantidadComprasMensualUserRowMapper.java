package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.CantidadComprasMensualUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CantidadComprasMensualUserRowMapper implements RowMapper<CantidadComprasMensualUserDTO> {

  @Override
  public CantidadComprasMensualUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CantidadComprasMensualUserDTO c = new CantidadComprasMensualUserDTO();
    DashboardUserMapperUtil.mapCantidadComprasAnual(c, rs);
    return c;
  }
}
