package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.LitrosVendidosAdminDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VentasLitrosAdminRowMapper implements RowMapper<LitrosVendidosAdminDTO> {
  @Override
  public LitrosVendidosAdminDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    LitrosVendidosAdminDTO dto = new LitrosVendidosAdminDTO();
    DashboardAdminMapperUtil.mapVentasLitrosAdmin(dto, rs);
    return dto;
  }
}
