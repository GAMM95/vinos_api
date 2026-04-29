package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.dto.view.CompraDTO;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CompraRowMapper;
import com.gamm.vinos_api.repository.ReporteRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReporteRepositoryImpl extends SimpleJdbcDAOBase implements ReporteRepository {

  // Queries de compras
  private static final String VW_COMPRAS = "SELECT * FROM vw_compras_usuario";
  private static final String VW_DETALLE_COMPRA = "SELECT * FROM vw_compras WHERE id_compra = ? ";

  public ReporteRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public List<CompraDTO> reporteDetalleCompra(Integer idCompra) {
    return jdbcTemplate.query(VW_DETALLE_COMPRA, new CompraRowMapper(), idCompra);
  }
}
