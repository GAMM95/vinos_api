package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.domain.view.PrecioView;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.PrecioStockDetalleRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.PrecioStockRowMapper;
import com.gamm.vinos_api.repository.PrecioRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PrecioRepositoryImpl extends SimpleJdbcDAOBase implements PrecioRepository {

  // Procedimiento almacenado
  private static final String SP_PRECIO = "sp_precio";

  // Query para listar los precios del stock
  private static final String VW_PRECIOS_STOCK = "SELECT * FROM vw_stock_precios";

  private static final String VW_PRECIOS_STOCK_DETALLE = "SELECT * FROM vw_stock_precios_detalle WHERE idVino = ? AND idSucursal = ? ORDER BY fechaInicio DESC";

  // Llamado al sp
  public SimpleJdbcCall spCall;

  public PrecioRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }


  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_PRECIO)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdVino", Types.TINYINT),
            new SqlParameter("pIdSucursal", Types.TINYINT),
            new SqlParameter("pPrecio", Types.DOUBLE),
            new SqlParameter("pNombreVino", Types.VARCHAR),
            new SqlOutParameter(PARAM_RESPUESTA, Types.TINYINT),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        )
        .returningResultSet(DEFAULT_RESULTSET, new PrecioStockRowMapper());
  }

  /* Helpers */
  private Map<String, Object> construirParametros(
      int tipo,
      Integer idVino,
      Integer idSucursal,
      BigDecimal precio,
      String nombreVino
  ) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdVino", idVino);
    params.put("pIdSucursal", idSucursal);
    params.put("pPrecio", precio);
    params.put("pNombreVino", nombreVino);
    return params;
  }

  @Override
  public ResultadoSP asignarPrecio(PrecioSucursal precio) {
    Map<String, Object> params = construirParametros(
        1,
        precio.getVino().getIdVino(),
        precio.getSucursal().getIdSucursal(),
        precio.getPrecioVenta(),
        null
    );
    return ejecutarSP(spCall, params);
  }

  @Override
  public List<PrecioView> listarTotalPreciosStock() {
    return jdbcTemplate.query(VW_PRECIOS_STOCK, new PrecioStockRowMapper());
  }

  @Override
  public List<PrecioView> listarPreciosStockSucursal(Integer idSucursal) {
    String sql = VW_PRECIOS_STOCK + " WHERE idSucursal  = ? ";
    return jdbcTemplate.query(sql, new PrecioStockRowMapper(), idSucursal);
  }

  @Override
  public ResultadoSP filtrarPorVinoOSucursal(String nombreVino, Integer idSucursal) {
    Map<String, Object> params = construirParametros(2, null, idSucursal, null, nombreVino);
    return ejecutarSPConLista(spCall, params);
  }

  @Override
  public List<PrecioView> listarPreciosDetalle(Integer idVino , Integer idSucursal) {
    return jdbcTemplate.query(VW_PRECIOS_STOCK_DETALLE, new PrecioStockDetalleRowMapper(), idVino, idSucursal);
  }

  @Override
  public ResultadoSP filtrarPorVino(String nombreVino, Integer idSucursal) {
    Map<String, Object> params = construirParametros(3, null, idSucursal, null, nombreVino);
    return ejecutarSPConLista(spCall, params);
  }
}
