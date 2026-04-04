package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.CantidadComprasMensualAdminDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CantidadComprasMensualAdminRowMapper implements RowMapper<CantidadComprasMensualAdminDTO> {
  @Override
  public CantidadComprasMensualAdminDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CantidadComprasMensualAdminDTO dto = new CantidadComprasMensualAdminDTO();
  DashboardAdminMapperUtil.mapCantidadComprasMensualAdmin(dto, rs);
    return dto;
  }
}
