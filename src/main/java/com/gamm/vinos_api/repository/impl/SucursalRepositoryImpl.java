package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.dto.view.SucursalView;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.SucursalRowMapper;
import com.gamm.vinos_api.repository.SucursalRepository;
import com.gamm.vinos_api.util.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SucursalRepositoryImpl extends SimpleJdbcDAOBase implements SucursalRepository {

  // Consultas SQL
  private static final String VIEW_SUCURSAL = "SELECT * FROM vw_sucursal";
  private static final String SP_SUCURSAL = "sp_sucursal";

  private SimpleJdbcCall spCall;

  public SucursalRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  // Metodo inicial para inicializar el sp
  @PostConstruct
  private void init() {
    // Configuracion inicial
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_SUCURSAL)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdSucursal", Types.TINYINT),
            new SqlParameter("pNombre", Types.VARCHAR),
            new SqlParameter("pUbicacion", Types.VARCHAR),
            new SqlParameter("pLatitud", Types.DECIMAL),
            new SqlParameter("pLongitud", Types.DECIMAL),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        // Agregar el soporte del resultser del rowMapper
        .returningResultSet("ResultSet", new SucursalRowMapper());
  }

  // Listar sucursales
  @Override
  public List<SucursalView> listarSucursales() {
    return jdbcTemplate.query(VIEW_SUCURSAL, new SucursalRowMapper());
  }

  // Registrar sucursal
  @Override
  public ResultadoSP registrarSucursal(Sucursal sucursal) {
    return ejecutarSP(1, sucursal);
  }

  @Override
  public ResultadoSP actualizarSucursal(Sucursal sucursal) {
    return ejecutarSP(2, sucursal);
  }

  @Override
  public ResultadoSP darDeBajaSucursal(Integer idSucursal) {
    Sucursal sucursal = new Sucursal();
    sucursal.setIdSucursal(idSucursal);
    return ejecutarSP(3, sucursal);
  }

  @Override
  public ResultadoSP darDeAltaSucursal(Integer idSucursal) {
    Sucursal sucursal = new Sucursal();
    sucursal.setIdSucursal(idSucursal);
    return ejecutarSP(4, sucursal);
  }

  @Override
  public ResultadoSP filtrarSucursal(String nombre) {
    Sucursal s = new Sucursal();
    s.setNombre(nombre);
    return ejecutarSPConLista(spCall, construirParametros(5, s));
  }

  /**
   * METODO AUXILIAR
   **/
  // Ejecutar el procedimiento almancenado
  private ResultadoSP ejecutarSP(int tipo, Sucursal sucursal) {
    return ejecutarSP(spCall, construirParametros(tipo, sucursal));
  }

  // Mapeo de los parametros para enviar al procedimiento
  private Map<String, Object> construirParametros(int tipo, Sucursal sucursal) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdSucursal", sucursal.getIdSucursal());
    params.put("pNombre", sucursal.getNombre());
    params.put("pUbicacion", sucursal.getUbicacion());
    params.put("pLatitud", sucursal.getLatitud());
    params.put("pLongitud", sucursal.getLongitud());
    return params;
  }
}
