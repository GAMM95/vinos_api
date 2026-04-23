package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.dto.view.CategoriaDTO;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.CategoriaRowMapper;
import com.gamm.vinos_api.repository.CategoriaRepository;
import com.gamm.vinos_api.util.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class CategoriaRepositoryImpl extends SimpleJdbcDAOBase implements CategoriaRepository {

  private static final String SP_CATEGORIA = "sp_categoria";
  private static final String VW_CATEGORIAS = "SELECT * FROM vw_categorias";

  private SimpleJdbcCall spCall;

  public CategoriaRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    spCall = new SimpleJdbcCall(dataSource)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(SP_CATEGORIA)
        .declareParameters(
            new SqlParameter("pTipo", Types.INTEGER),
            new SqlParameter("pIdCategoria", Types.INTEGER),
            new SqlParameter("pNombre", Types.VARCHAR),
            new SqlParameter("pDescripcion", Types.VARCHAR),
            new SqlOutParameter(PARAM_RESPUESTA, Types.INTEGER),
            new SqlOutParameter(PARAM_MENSAJE, Types.VARCHAR)
        );
  }

  // Registrar categorias
  @Override
  public ResultadoSP registrarCategoria(Categoria categoria) {
    return ejecutarSP(spCall, construirParametros(1, categoria));
  }

  // Actualizar categorias
  @Override
  public ResultadoSP actualizarCategoria(Categoria categoria) {
    return ejecutarSP(spCall, construirParametros(2, categoria));
  }

  // Cambiar estado
  @Override
  public ResultadoSP cambiarEstado(Integer idCategoria) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", 3);
    params.put("pIdCategoria", idCategoria);
    params.put("pNombre", null);
    params.put("pDescripcion", null);

    return ejecutarSP(spCall, params);
  }

  // Listar categorias
  @Override
  public List<CategoriaDTO> listarCategorias() {
    return jdbcTemplate.query(VW_CATEGORIAS, new CategoriaRowMapper());
  }

  /// MÉTODOS PRIVADOS AUXILIARES
  private Map<String, Object> construirParametros(int tipo, Categoria categoria) {
    Map<String, Object> params = new HashMap<>();
    params.put("pTipo", tipo);
    params.put("pIdCategoria", categoria.getIdCategoria());
    params.put("pNombre", categoria.getNombre());
    params.put("pDescripcion", categoria.getDescripcion());
    return params;
  }
}