package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Proveedor;
import com.gamm.vinos_api.domain.view.ProveedorView;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.ProveedorRowMapper;
import com.gamm.vinos_api.repository.ProveedorRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
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
public class ProveedorRepositoryImpl extends SimpleJdbcDAOBase implements ProveedorRepository {

  private static final String SP_PROVEEDOR = "sp_proveedor";
  private static final String VW_PROVEEDORES = "SELECT * FROM vw_proveedores";
  private SimpleJdbcCall spCall;

  public ProveedorRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_PROVEEDOR)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdProveedor", Types.TINYINT),
            new SqlParameter("pRazonSocial", Types.VARCHAR),
            new SqlParameter("pRuc", Types.VARCHAR),
            new SqlParameter("pContacto", Types.VARCHAR),
            new SqlParameter("pUbicacion", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        .returningResultSet("ResultSet", new ProveedorRowMapper());
  }

  /// MÉTODOS CRUD SIMPLIFICADOS
  // Registro de proveedores
  @Override
  public ResultadoSP registrarProveedor(Proveedor proveedor) {
    return ejecutarSP(1, proveedor);
  }

  // Actualización de proveedores
  @Override
  public ResultadoSP actualizarProveedor(Proveedor proveedor) {
    return ejecutarSP(2, proveedor);
  }

  // Dar de baja a proveedor
  @Override
  public ResultadoSP darDeBajaProveedor(Integer idProveedor) {
    Proveedor proveedor = new Proveedor();
    proveedor.setIdProveedor(idProveedor);
    return ejecutarSP(3, proveedor);
  }

  // Activar o dar de alta a proveedor
  @Override
  public ResultadoSP darDeAltaProveedor(Integer idProveedor) {
    Proveedor proveedor = new Proveedor();
    proveedor.setIdProveedor(idProveedor);
    return ejecutarSP(4, proveedor);
  }

  // Filtrar proveedor por nombre o contacto
  @Override
  public ResultadoSP filtrarProveedor(String termino) {
    Proveedor proveedor = new Proveedor();
    proveedor.setRazonSocial(termino);
    proveedor.setRuc(termino);
    proveedor.setContacto(termino);
    return ejecutarSPConLista(spCall, construirParametros(5, proveedor));
  }

  @Override
  public List<ProveedorView> listarProveedores() {
    return jdbcTemplate.query(VW_PROVEEDORES, new ProveedorRowMapper());
  }

  /// MÉTODOs AUXILIAR CENTRALIZADO
  private ResultadoSP ejecutarSP(int tipo, Proveedor proveedor) {
    return ejecutarSP(spCall, construirParametros(tipo, proveedor));
  }

  private Map<String, Object> construirParametros(int tipo, Proveedor proveedor) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdProveedor", proveedor.getIdProveedor());
    params.put("pRazonSocial", proveedor.getRazonSocial());
    params.put("pRuc", proveedor.getRuc());
    params.put("pContacto", proveedor.getContacto());
    params.put("pUbicacion", proveedor.getUbicacion());
    return params;
  }
}
