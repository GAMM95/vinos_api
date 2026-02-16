package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.view.CatalogoView;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.jdbc.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CatalogoRowMapper;
import com.gamm.vinos_api.repository.CatalogoRepository;
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
public class CatalogoRepositoryImpl extends SimpleJdbcDAOBase implements CatalogoRepository {

  private static final String SP_CATALOGO = "sp_catalogo";
  private static final String VW_CATALOGOS = "SELECT * FROM vw_catalogo";

  private SimpleJdbcCall spCall;

  public CatalogoRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_CATALOGO)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdCatalogo", Types.SMALLINT),
            new SqlParameter("pIdProveedor", Types.TINYINT),
            new SqlParameter("pIdVino", Types.TINYINT),
            new SqlParameter("pIdPresentacion", Types.TINYINT),
            new SqlParameter("pPrecioUnidad", Types.DECIMAL),
            new SqlParameter("pTipoVino", Types.VARCHAR),
//            new SqlParameter("pTermino", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        .returningResultSet("ResultSet", new CatalogoRowMapper());
  }

  /// MÉTODOS CRUD
  // Registrar catalogos
  @Override
  public ResultadoSP registrarCatalogo(Catalogo catalogo) {
    return ejecutarSP(1, catalogo);
  }

  // Actualizar catálogos
  @Override
  public ResultadoSP actualizarCatalogo(Catalogo catalogo) {
    return ejecutarSP(2, catalogo);
  }

  // Dar de baja a un catálogo
  @Override
  public ResultadoSP darDeBajaCatalogo(Integer idCatalogo) {
    Catalogo catalogo = new Catalogo();
    catalogo.setIdCatalogo(idCatalogo);
    return ejecutarSP(4, catalogo);
  }

  // Dar de alta a un catálogo
  @Override
  public ResultadoSP darDeAltaCatalogo(Integer idCatalogo) {
    Catalogo catalogo = new Catalogo();
    catalogo.setIdCatalogo(idCatalogo);
    return ejecutarSP(5, catalogo);
  }

  // Listar catalogos
  @Override
  public List<CatalogoView> listarCatalogos() {
    return jdbcTemplate.query(VW_CATALOGOS, new CatalogoRowMapper());
  }

  @Override
  public List<CatalogoView> listarCatalogosPaginados(Integer idProveedor, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VW_CATALOGOS + " WHERE idProveedor = ? ORDER BY vw_catalogo.nombreVino LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new CatalogoRowMapper(), idProveedor, limite, offset);
  }

  @Override
  public Long contarCatalogos(Integer idProveedor) {
    String sql = "SELECT COUNT(*) FROM vw_catalogo WHERE idProveedor = ?";
    return jdbcTemplate.queryForObject(sql, Long.class, idProveedor);
  }

  // Filtrar por codigo de algun proveedor
  @Override
  public ResultadoSP filtrarPorProveedor(Integer idProveedor) {
    Catalogo catalogo = new Catalogo();
    catalogo.setIdProveedor(idProveedor);
    return ejecutarSPConLista(spCall, construirParametros(3, catalogo));
  }

  /// MÉTODOS PRIVADOS AUXILIARES
  // Ejecuta el procedimiento almacenado con los parámetros dados
  private ResultadoSP ejecutarSP(int tipo, Catalogo catalogo) {
    return ejecutarSP(spCall, construirParametros(tipo, catalogo));
  }

  // Arma los parámetros comunes del SP
  private Map<String, Object> construirParametros(int tipo, Catalogo catalogo) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdCatalogo", catalogo.getIdCatalogo());
    params.put("pIdProveedor", catalogo.getIdProveedor());
    params.put("pIdVino", catalogo.getIdVino());
    params.put("pIdPresentacion", catalogo.getIdPresentacion());
    params.put("pPrecioUnidad", catalogo.getPrecioUnidad());
    params.put("pTipoVino",
        catalogo.getTipoVino() != null ? catalogo.getTipoVino().getValorBD() : null
    );
    return params;
  }
}