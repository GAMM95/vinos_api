package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.dto.view.VinoView;
import com.gamm.vinos_api.dto.view.VinosCompraView;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.VinoRowMapper;
import com.gamm.vinos_api.jdbc.rowmapper.VinosCompraViewMapper;
import com.gamm.vinos_api.repository.VinoRepository;
import com.gamm.vinos_api.util.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

@Repository
public class VinoRepositoryImpl extends SimpleJdbcDAOBase implements VinoRepository {

  private static final String SP_VINOS = "sp_vino";
  private static final String VIEW_VINOS = "SELECT * FROM vw_vinos";
  private static final String VIEW_VINOS_COMPRA = "SELECT * FROM vw_listaVinosCompra";

  private SimpleJdbcCall spCall;
  private SimpleJdbcCall spCallCompra;

  public VinoRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = crearSimpleJdbcCall(new VinoRowMapper());
    spCallCompra = crearSimpleJdbcCall(new VinosCompraViewMapper());
  }

  // SimpleJdbcCall genérico para SP_VINOS.
  private SimpleJdbcCall crearSimpleJdbcCall(RowMapper<?> rowMapper) {
    return new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_VINOS)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pIdVino", Types.TINYINT),
            new SqlParameter("pNombre", Types.VARCHAR),
            new SqlParameter("pIdCategoria", Types.TINYINT),
            new SqlParameter("pDescripcion", Types.VARCHAR),
            new SqlParameter("pProveedores", Types.VARCHAR),
            new SqlParameter("pCategorias", Types.VARCHAR),
            new SqlParameter("pPresentaciones", Types.VARCHAR),
            new SqlParameter("pTiposVino", Types.VARCHAR),
            new SqlParameter("pOrigenVino", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.TINYINT),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        .returningResultSet("ResultSet", rowMapper);
  }

  /*** CRUD básico ***/
  @Override
  public ResultadoSP registrarVino(Vino vino) {
    return ejecutarSP(1, vino);
  }

  @Override
  public ResultadoSP actualizarVino(Vino vino) {
    return ejecutarSP(2, vino);
  }

  @Override
  public ResultadoSP eliminarVinoPorId(Integer idVino) {
    Vino vino = new Vino();
    vino.setIdVino(idVino);
    return ejecutarSP(3, vino);
  }

  /*** Consultas simples ***/
  @Override
  public ResultadoSP filtrarVinoPorNombre(String nombre) {
    return ejecutarSPConLista(spCall, construirParametrosBasicos(nombre));
  }

  @Override
  public List<VinoView> listarVinos() {
    return jdbcTemplate.query(VIEW_VINOS, new VinoRowMapper());
  }

  @Override
  public List<VinoView> listarVinosPaginados(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    return jdbcTemplate.query(VIEW_VINOS + " LIMIT ? OFFSET ?", new VinoRowMapper(), limite, offset);
  }

  @Override
  public Long contarVinos() {
    return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM vw_vinos", Long.class);
  }

  /*** Compra ***/
  @Override
  public List<VinosCompraView> listarVinosParaCompra() {
    return jdbcTemplate.query(VIEW_VINOS_COMPRA, new VinosCompraViewMapper());
  }

  @Override
  public List<VinosCompraView> listarVinosParaCompraPaginados(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    return jdbcTemplate.query(VIEW_VINOS_COMPRA + " LIMIT ? OFFSET ?", new VinosCompraViewMapper(), limite, offset);
  }

  @Override
  public Long contarVinosParaCompra() {
    return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM vw_listaVinosCompra", Long.class);
  }

  @Override
  public ResultadoSP filtrarVinosParaCompra(String nombre, String proveedores, String categorias,
                                            String presentaciones, String tiposVino, String origenVino) {
    return ejecutarSPConLista(spCallCompra,
        construirParametrosCompra(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino));
  }

  @Override
  public List<VinosCompraView> filtrarVinosParaCompraPaginados(String nombre, String proveedores,
                                                               String categorias, String presentaciones,
                                                               String tiposVino, String origenVino,
                                                               int pagina, int limite) {
    List<VinosCompraView> filtrados = ejecutarSPCompraLista(spCallCompra,
        construirParametrosCompra(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino));
    return paginarLista(filtrados, pagina, limite);
  }

  @Override
  public Long contarVinosParaCompraFiltrados(String nombre, String proveedores,
                                             String categorias, String presentaciones,
                                             String tiposVino, String origenVino) {
    List<VinosCompraView> filtrados = ejecutarSPCompraLista(spCallCompra,
        construirParametrosCompra(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino));
    return (long) filtrados.size();
  }

  /*** Métodos privados auxiliares ***/
  private ResultadoSP ejecutarSP(int tipo, Vino vino) {
    return ejecutarSP(spCall, construirParametros(tipo, vino));
  }

  /** Hardcoder tipo = 4 para consultas básicas, eliminando parámetro innecesario */
  private Map<String, Object> construirParametrosBasicos(String nombre) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", 4);
    params.put("pNombre", nombre);
    params.put("pIdVino", null);
    params.put("pIdCategoria", null);
    params.put("pDescripcion", null);
    params.put("pProveedores", null);
    params.put("pCategorias", null);
    params.put("pPresentaciones", null);
    params.put("pTiposVino", null);
    params.put("pOrigenVino", null);
    return params;
  }

  private Map<String, Object> construirParametros(int tipo, Vino vino) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdVino", vino.getIdVino());
    params.put("pNombre", vino.getNombre());
    params.put("pIdCategoria", vino.getCategoria() != null ? vino.getCategoria().getIdCategoria() : null);
    params.put("pDescripcion", vino.getDescripcion());
    // Filtros no aplicables
    params.put("pProveedores", null);
    params.put("pCategorias", null);
    params.put("pPresentaciones", null);
    params.put("pTiposVino", null);
    params.put("pOrigenVino", null);
    return params;
  }

  private Map<String, Object> construirParametrosCompra(String nombre, String proveedores, String categorias,
                                                        String presentaciones, String tiposVino, String origenVino) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", 5);
    params.put("pIdVino", null);
    params.put("pNombre", nombre);
    params.put("pIdCategoria", null);
    params.put("pDescripcion", null);
    params.put("pProveedores", proveedores);
    params.put("pCategorias", categorias);
    params.put("pPresentaciones", presentaciones);
    params.put("pTiposVino", tiposVino);
    params.put("pOrigenVino", origenVino);
    return params;
  }

  private <T> List<T> ejecutarSPCompraLista(SimpleJdbcCall spCall, Map<String, Object> params) {
    Map<String, Object> out = spCall.execute(params);
    @SuppressWarnings("unchecked")
    List<T> result = (List<T>) out.get("ResultSet");
    return result != null ? result : Collections.emptyList();
  }

  private <T> List<T> paginarLista(List<T> lista, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    if (offset >= lista.size()) return Collections.emptyList();
    int end = Math.min(offset + limite, lista.size());
    return lista.subList(offset, end);
  }
}
