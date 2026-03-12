package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Categoria;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
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
    private static final String VW_CATEGORIAS = "SELECT idCategoria, nombre, descripcion, estado FROM vw_categorias";
    private static final String CBO_CATEGORIAS = "SELECT idCategoria, nombre FROM cbo_categoria";

    private SimpleJdbcCall spCall;

    public CategoriaRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @PostConstruct
    private void init() {
        spCall = new SimpleJdbcCall(jdbcTemplate)
            .withoutProcedureColumnMetaDataAccess()
            .withProcedureName(SP_CATEGORIA)
                .declareParameters(
                        new SqlParameter("pTipo", Types.TINYINT),
                        new SqlParameter("pIdCategoria", Types.TINYINT),
                        new SqlParameter("pNombre", Types.VARCHAR),
                        new SqlParameter("pDescripcion", Types.VARCHAR),
                        new SqlOutParameter("pRespuesta", Types.TINYINT),
                        new SqlOutParameter("pMensaje", Types.VARCHAR)
                );
    }

    /// MÉTODOS CRUD
    // Registrar categorias
    @Override
    public ResultadoSP registrarCategoria(Categoria categoria) {
        return ejecutarSP(1, categoria);
    }

    // Actualizar categorias
    @Override
    public ResultadoSP actualizarCategoria(Categoria categoria) {
        return ejecutarSP(2, categoria);
    }

    // Cambiar estado
    @Override
    public ResultadoSP cambiarEstado(Integer idCategoria) {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);
        categoria.setNombre("");
        categoria.setDescripcion("");
        return ejecutarSP(3, categoria);
    }

    // Listar categorias
    @Override
    public List<Categoria> listarCategorias() {
        return jdbcTemplate.query(VW_CATEGORIAS, new BeanPropertyRowMapper<>(Categoria.class));
    }

    // Listar combo de categorías
    @Override
    public List<Categoria> comboCategorias() {
        return jdbcTemplate.query(CBO_CATEGORIAS, new BeanPropertyRowMapper<>(Categoria.class));
    }

    /// MÉTODOS PRIVADOS AUXILIARES
    // Ejecuta el procedimiento almacenado con los parámetros dados
    private ResultadoSP ejecutarSP(int tipo, Categoria categoria) {
        return ejecutarSP(spCall, construirParametros(tipo, categoria));
    }

    // Arma los parámetros comunes del SP
    private Map<String, Object> construirParametros(int tipo, Categoria categoria) {
        Map<String, Object> params = new HashMap<>();
        params.put("pTipo", tipo);
        params.put("pIdCategoria", categoria.getIdCategoria());
        params.put("pNombre", categoria.getNombre());
        params.put("pDescripcion", categoria.getDescripcion());
        return params;
    }
}