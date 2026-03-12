package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.dto.view.AlmacenView;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.AlmacenDetalladoRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.AlmacenStockOrigenRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.AlmacenStockVinoRowMapper;
import com.gamm.vinos_api.repository.AlmacenRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AlmacenRepositoryImpl extends SimpleJdbcDAOBase implements AlmacenRepository {

  // Queries para las consultas
  private static final String VW_STOCK_DETALLADO = "SELECT * FROM vw_stock_almacen_detallado";
  private static final String VW_STOCK_VINO = "SELECT * FROM vw_stock_almacen_por_vino";
  private static final String VW_STOCK_ORIGEN = "SELECT * FROM vw_stock_almacen_por_origen";

  public AlmacenRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public List<AlmacenView> listarStockDetallado() {
    return jdbcTemplate.query(VW_STOCK_DETALLADO, new AlmacenDetalladoRowMapper());
  }

  @Override
  public List<AlmacenView> listarStockPorVino() {
    return jdbcTemplate.query(VW_STOCK_VINO, new AlmacenStockVinoRowMapper());
  }

  @Override
  public List<AlmacenView> listarStockPorOrigen() {
    return jdbcTemplate.query(VW_STOCK_ORIGEN, new AlmacenStockOrigenRowMapper());
  }
}
