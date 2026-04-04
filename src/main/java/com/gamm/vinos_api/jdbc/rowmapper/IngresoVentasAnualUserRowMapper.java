package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.IngresoVentasAnualUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngresoVentasAnualUserRowMapper implements RowMapper<IngresoVentasAnualUserDTO> {
  @Override
  public IngresoVentasAnualUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    IngresoVentasAnualUserDTO dto = new IngresoVentasAnualUserDTO();
    DashboardUserMapperUtil.mapIngresoVentasAnualUser(dto, rs);
    return dto;
  }
}
