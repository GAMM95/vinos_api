package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.dto.view.StockView;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.StockSucursalDetalladoRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.StockSucursalRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.StockVentaRowMapper;
import com.gamm.vinos_api.repository.StockRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StockRepositoryImpl extends SimpleJdbcDAOBase implements StockRepository {

  // Queries para listar stock
  private static final String VW_STOCK = "SELECT * FROM vw_stock_sucursal";
  private static final String VW_STOCK_SUCURSAL = "SELECT * FROM vw_stock_sucursal_detallado WHERE idSucursal = ?";
  private static final String VW_STOCK_VENTA = "SELECT * FROM vw_stock_venta WHERE idSucursal = ?";

  public StockRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public List<StockView> listarStockSucursal() {
    return jdbcTemplate.query(VW_STOCK, new StockSucursalRowMapper());
  }

  @Override
  public List<StockView> listarStockSucursalDetalladoPorSucursal(Integer idSucursal) {
    return jdbcTemplate.query(VW_STOCK_SUCURSAL, new StockSucursalDetalladoRowMapper(), idSucursal);
  }

  @Override
  public List<StockView> listarStockPorSucursal(Integer idSucursal) {
    return jdbcTemplate.query(VW_STOCK_SUCURSAL, new StockSucursalDetalladoRowMapper(), idSucursal);
  }

  @Override
  public List<StockView> listarStockVenta(Integer idSucursal) {
    return jdbcTemplate.query(VW_STOCK_VENTA, new StockVentaRowMapper(), idSucursal);
  }
}
