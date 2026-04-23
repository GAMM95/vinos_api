package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.view.AlmacenDTO;
import com.gamm.vinos_api.jdbc.rowmapper.utils.AlmacenMapperUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlmacenDetalladoRowMapper implements RowMapper<AlmacenDTO> {
  @Override
  public AlmacenDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    AlmacenDTO v = new AlmacenDTO();
    AlmacenMapperUtil.mapAlmacenBase(v, rs);
    AlmacenMapperUtil.mapDetallado(v, rs);
    return v;
  }
}
