package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.VentasLitrosUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VentasLitrosUserRowMapper implements RowMapper<VentasLitrosUserDTO> {
  @Override
  public VentasLitrosUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    VentasLitrosUserDTO dto = new VentasLitrosUserDTO();
    DashboardUserMapperUtil.mapVentasLitros(dto, rs);
    return dto;
  }
}
