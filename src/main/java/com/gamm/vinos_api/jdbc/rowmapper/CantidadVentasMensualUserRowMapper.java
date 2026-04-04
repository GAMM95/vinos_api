package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.CantidadVentasMensualUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CantidadVentasMensualUserRowMapper implements RowMapper<CantidadVentasMensualUserDTO> {
  @Override
  public CantidadVentasMensualUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CantidadVentasMensualUserDTO v = new CantidadVentasMensualUserDTO();
    DashboardUserMapperUtil.mapCantidadVentasAnual(v, rs);
    return v;
  }
}
