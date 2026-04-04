package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.IngresoVentasUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngresoVentasUserRowMapper implements RowMapper<IngresoVentasUserDTO> {

  @Override
  public IngresoVentasUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    IngresoVentasUserDTO v = new IngresoVentasUserDTO();
    DashboardUserMapperUtil.mapIngresoVentasUser(v, rs);
    return v;
  }
}
