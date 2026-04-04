package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.CantidadVentasMensualAdminDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CantidadVentasMensualAdminRowMapper implements RowMapper<CantidadVentasMensualAdminDTO> {
  @Override
  public CantidadVentasMensualAdminDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CantidadVentasMensualAdminDTO dto = new CantidadVentasMensualAdminDTO();
    DashboardAdminMapperUtil.mapCantidadVentasMensualAdmin(dto, rs);
    return dto;
  }
}
