package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.VinoMasVendidoUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VinoMasVendidoUserRowMapper implements RowMapper<VinoMasVendidoUserDTO> {
  @Override
  public VinoMasVendidoUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    VinoMasVendidoUserDTO v = new VinoMasVendidoUserDTO();
    DashboardUserMapperUtil.mapVinoMasVendido(v, rs);
    return v;
  }
}
