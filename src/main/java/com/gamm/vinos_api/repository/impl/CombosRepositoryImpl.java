package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.dto.cbo.*;
import com.gamm.vinos_api.jdbc.rowmapper.*;
import com.gamm.vinos_api.repository.CombosRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CombosRepositoryImpl implements CombosRepository {
  private static final String CBO_UNIDAD_VOLUMEN = "SELECT * FROM cbo_unidadVolumen";
  private static final String CBO_CATEGORIA = "SELECT * FROM cbo_categoria";
  private static final String CBO_PROVEEDOR = "SELECT * FROM cbo_proveedor";
  private static final String CBO_PRESENTACION = "SELECT * FROM cbo_presentacion";
  private static final String CBO_VINO = "SELECT * FROM cbo_vino";
  private static final String CBO_SUCURSAL = "SELECT * FROM cbo_sucursal";
  private static final String CHK_PRESENTACION = "SELECT * FROM chk_presentacion";
  private static final String CBO_USUARIO = "SELECT * FROM cbo_usuario";
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

  @Override
  public List<ProveedorCbo> comboProveedor() {
    return jdbcTemplate.query(CBO_PROVEEDOR, new ProveedorCboRowMapper());
  }

  @Override
  public List<PresentacionCbo> comboPresentacion() {
    return jdbcTemplate.query(CBO_PRESENTACION, new PresentacionCboRowMapper());
  }

  @Override
  public List<VinoCbo> comboVino() {
    return jdbcTemplate.query(CBO_VINO, new VinoCboRowMapper());
  }

  @Override
  public List<SucursalCbo> comboSucursal() {
    return jdbcTemplate.query(CBO_SUCURSAL, new SucursalCboRowMapper());
  }

  @Override
  public List<PresentacionChk> checkBoxPresentacion() {
    return jdbcTemplate.query(CHK_PRESENTACION, new PresentacionChkRowMapper());
  }

  @Override
  public List<UsuarioCbo> comboUsuario() {
    return  jdbcTemplate.query(CBO_USUARIO, new UsuarioCboRowMapper());
  }
}
