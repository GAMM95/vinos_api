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
            new SqlOutParameter(PARAM_RESPUESTA, Types.TINYINT),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        )
        .returningResultSet(DEFAULT_RESULTSET, rowMapper);
  }

  // ─── CRUD ─────────────────────────────────────────────────────────────────

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

  // ─── Consultas simples ────────────────────────────────────────────────────

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

  // ─── Compra ───────────────────────────────────────────────────────────────

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
  public ResultadoSP filtrarVinosParaCompra(
      String nombre, String proveedores, String categorias,
      String presentaciones, String tiposVino, String origenVino
  ) {
    return ejecutarSPConLista(spCallCompra, construirParametrosCompra(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino));
  }

  @Override
  public List<VinosCompraView> filtrarVinosParaCompraPaginados(
      String nombre, String proveedores, String categorias,
      String presentaciones, String tiposVino, String origenVino,
      int pagina, int limite
  ) {
    List<VinosCompraView> filtrados = ejecutarSPYObtenerLista(spCallCompra,
        construirParametrosCompra(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino));
    return paginarLista(filtrados, pagina, limite);
  }

  @Override
  public Long contarVinosParaCompraFiltrados(
      String nombre, String proveedores, String categorias,
      String presentaciones, String tiposVino, String origenVino
  ) {
    List<VinosCompraView> filtrados = ejecutarSPYObtenerLista(spCallCompra,
        construirParametrosCompra(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino));
    return (long) filtrados.size();
  }

  // ─── Helpers privados ─────────────────────────────────────────────────────

  private ResultadoSP ejecutarSP(int tipo, Vino vino) {
    return ejecutarSP(spCall, construirParametros(tipo, vino));
  }

  private Map<String, Object> construirParametrosBasicos(String nombre) {
    return construirParametros(4, nombre, null, null, null, null, null, null, null, null);
  }

  private Map<String, Object> construirParametros(int tipo, Vino vino) {
    return construirParametros(
        tipo,
        vino.getNombre(),
        vino.getIdVino(),
        vino.getCategoria() != null ? vino.getCategoria().getIdCategoria() : null,
        vino.getDescripcion(),
        null, null, null, null, null
    );
  }

  private Map<String, Object> construirParametrosCompra(
      String nombre, String proveedores, String categorias,
      String presentaciones, String tiposVino, String origenVino
  ) {
    return construirParametros(5, nombre, null, null, null,
        proveedores, categorias, presentaciones, tiposVino, origenVino);
  }

  // ✅ Un solo método que construye todos los parámetros — elimina duplicación
  private Map<String, Object> construirParametros(
      int tipo, String nombre, Integer idVino, Integer idCategoria,
      String descripcion, String proveedores, String categorias,
      String presentaciones, String tiposVino, String origenVino
  ) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo",          tipo);
    params.put("pIdVino",        idVino);
    params.put("pNombre",        nombre);
    params.put("pIdCategoria",   idCategoria);
    params.put("pDescripcion",   descripcion);
    params.put("pProveedores",   proveedores);
    params.put("pCategorias",    categorias);
    params.put("pPresentaciones", presentaciones);
    params.put("pTiposVino",     tiposVino);
    params.put("pOrigenVino",    origenVino);
    return params;
  }

}
