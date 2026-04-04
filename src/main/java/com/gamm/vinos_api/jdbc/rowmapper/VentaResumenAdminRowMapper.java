package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.VentaResumenAdminDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VentaResumenAdminRowMapper implements RowMapper<VentaResumenAdminDTO> {

  @Override
  public VentaResumenAdminDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    VentaResumenAdminDTO v = new VentaResumenAdminDTO();
    DashboardAdminMapperUtil.mapVentaResumenAdmin(v, rs);
    return v;
  }
}
