package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.CantidadVentasUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CantidadVentasUserRowMapper implements RowMapper<CantidadVentasUserDTO> {

  @Override
  public CantidadVentasUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CantidadVentasUserDTO v = new CantidadVentasUserDTO();
    DashboardUserMapperUtil.mapVentasTotal(v, rs);
    return v;
  }
}