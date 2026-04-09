package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.BalanceNetoMensualUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceNetoMensualUserRowMapper implements RowMapper<BalanceNetoMensualUserDTO> {

  @Override
  public BalanceNetoMensualUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    BalanceNetoMensualUserDTO dto = new BalanceNetoMensualUserDTO();
    DashboardUserMapperUtil.mapBalanceNetoMensual(dto, rs);
    return dto;
  }
}
