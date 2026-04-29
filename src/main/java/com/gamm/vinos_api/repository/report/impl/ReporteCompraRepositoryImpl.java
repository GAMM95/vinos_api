package com.gamm.vinos_api.repository.report.impl;

import com.gamm.vinos_api.dto.view.CompraDTO;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CompraRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.DetalleCompraUserRowMapper;
import com.gamm.vinos_api.repository.report.ReporteCompraRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReporteCompraRepositoryImpl extends SimpleJdbcDAOBase implements ReporteCompraRepository {

  // Queries
  private static final String BASE = "SELECT * FROM vw_compras_usuario";
  private static final String DETALLE = "SELECT * FROM vw_compras WHERE idCompra = ?";

  public ReporteCompraRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public List<CompraDTO> listarTodas() {
    return jdbcTemplate.query(BASE, new CompraRowMapper());
  }

  @Override
  public List<CompraDTO> listarDetalle(Integer idCompra) {
    return jdbcTemplate.query(DETALLE, new DetalleCompraUserRowMapper(), idCompra);
  }
}
