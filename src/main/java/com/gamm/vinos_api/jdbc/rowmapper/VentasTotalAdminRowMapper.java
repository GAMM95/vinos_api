package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.VentasTotalAdminDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VentasTotalAdminRowMapper implements RowMapper<VentasTotalAdminDTO> {

  @Override
  public VentasTotalAdminDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    VentasTotalAdminDTO v = new VentasTotalAdminDTO();
    DashboardAdminMapperUtil.mapVentasTotalAdmin(v, rs);
    return v;
  }
}