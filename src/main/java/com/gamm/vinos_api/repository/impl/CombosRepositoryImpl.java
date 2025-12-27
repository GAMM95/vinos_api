package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.view.CategoriaCbo;
import com.gamm.vinos_api.domain.view.UnidadVolumenCbo;
import com.gamm.vinos_api.jdbc.rowmapper.CategoriaRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.UnidadVolumenRowMapper;
import com.gamm.vinos_api.repository.CombosRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CombosRepositoryImpl implements CombosRepository {
  private static final String CBO_UNIDAD_VOLUMEN = "SELECT * FROM cbo_unidadVolumen";
  private static final String CBO_CATEGORIA = "SELECT * FROM cbo_categoria";
  private final JdbcTemplate jdbcTemplate;

  public CombosRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<UnidadVolumenCbo> comboUnidadVolumen() {
    return jdbcTemplate.query(CBO_UNIDAD_VOLUMEN, new UnidadVolumenRowMapper());
  }

  @Override
  public List<CategoriaCbo> comboCategoria() {
    return jdbcTemplate.query(CBO_CATEGORIA, new CategoriaRowMapper());
  }
}
