package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.cbo.CategoriaCbo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaRowMapper implements RowMapper<CategoriaCbo> {

  @Override
  public CategoriaCbo mapRow(ResultSet rs, int rowNum) throws SQLException {
    CategoriaCbo categoriaCbo = new CategoriaCbo();
    categoriaCbo.setIdCategoria(rs.getInt("idCategoria"));
    categoriaCbo.setNombreCategoria(rs.getString("nombreCategoria"));
    return categoriaCbo;
  }
}
