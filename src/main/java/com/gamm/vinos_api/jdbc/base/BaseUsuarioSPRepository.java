package com.gamm.vinos_api.jdbc.base;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.util.ResultadoSP;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseUsuarioSPRepository extends SimpleJdbcDAOBase {

  protected SimpleJdbcCall spCall;

  protected BaseUsuarioSPRepository(DataSource dataSource) {
    super(dataSource);
  }

  protected void initCommon(String spName, RowMapper<?> mapper) {
    this.spCall = new SimpleJdbcCall(jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withProcedureName(spName)
        .declareParameters(
            new SqlParameter("pTipo", Types.TINYINT),
            new SqlParameter("pNombres", Types.VARCHAR),
            new SqlParameter("pApellidoPaterno", Types.VARCHAR),
            new SqlParameter("pApellidoMaterno", Types.VARCHAR),
            new SqlParameter("pCelular", Types.VARCHAR),
            new SqlParameter("pEmail", Types.VARCHAR),
            new SqlParameter("pDomicilio", Types.VARCHAR),
            new SqlParameter("pIdUsuario", Types.INTEGER),
            new SqlParameter("pUsername", Types.VARCHAR),
            new SqlParameter("pRutaFoto", Types.VARCHAR),
            new SqlParameter("pPassword", Types.VARCHAR),
            new SqlParameter("pPasswordNueva", Types.VARCHAR),
            new SqlParameter("pIdSucursal", Types.TINYINT),
            new SqlParameter("pTerminoBusqueda", Types.VARCHAR),
            new SqlOutParameter("pRespuesta", Types.INTEGER),
            new SqlOutParameter("pMensaje", Types.VARCHAR)
        )
        .returningResultSet("ResultSet", mapper);
  }

  // ENFOQUE: para actualizaciones parciales, enviamos null si el campo no fue provisto por el cliente
  protected Map<String, Object> construirParametros(int tipo, Usuario u) {
    Map<String, Object> p = new HashMap<>();

    p.put("pTipo", tipo);

    // Campos que pueden venir null para indicar "no cambiar"
    p.put("pIdUsuario", u.getIdUsuario()); // para operaciones que requieren id
    p.put("pUsername", u.getUsername()); // si null => no actualizar username
    p.put("pRutaFoto", u.getRutaFoto()); // si null => no actualizar foto
    p.put("pPassword", u.getPassword()); // solo para tipos 7/8 si viene
    p.put("pPasswordNueva", u.getPasswordNueva()); // para tipos 7/8
    // pTerminoBusqueda solo para tipo 5
    p.put("pTerminoBusqueda", null);

    if (u.getPersona() != null) {
      // Si la propiedad en persona es null, dejamos null (no forzamos valor)
      p.put("pNombres", u.getPersona().getNombres());
      p.put("pApellidoPaterno", u.getPersona().getApellidoPaterno());
      p.put("pApellidoMaterno", u.getPersona().getApellidoMaterno());
      p.put("pCelular", u.getPersona().getCelular());
      p.put("pEmail", u.getPersona().getEmail());
      p.put("pDomicilio", u.getPersona().getDomicilio());
    } else {
      p.put("pNombres", null);
      p.put("pApellidoPaterno", null);
      p.put("pApellidoMaterno", null);
      p.put("pCelular", null);
      p.put("pEmail", null);
      p.put("pDomicilio", null);
    }

    // Agregamos la sucursal si existe
    if (u.getSucursal() != null && u.getSucursal().getIdSucursal() != null) {
      p.put("pIdSucursal", u.getSucursal().getIdSucursal());
    } else {
      p.put("pIdSucursal", null);
    }

    // Ajustes por tipo
    if (tipo == 5) {
      // filtrar: usar username como termino si se pasó por username o si pTerminoBusqueda fue rellenado en el Usuario (no es ideal)
      p.put("pTerminoBusqueda", u.getUsername());
    }

    return p;
  }

  protected ResultadoSP ejecutarSP(Map<String, Object> params) {
    Map<String, Object> r = spCall.execute(params);

    return new ResultadoSP(
        (r.get("pRespuesta") != null) ? ((Number) r.get("pRespuesta")).intValue() : 0,
        (String) r.get("pMensaje"),
        null
    );
  }

  @SuppressWarnings("unchecked")
  protected <T> ResultadoSP ejecutarSPConLista(Map<String, Object> params, boolean devolverListaCompleta) {
    Map<String, Object> r = spCall.execute(params);

    List<T> lista = (List<T>) r.get("ResultSet");

    Object data;

    if (devolverListaCompleta) {
      data = lista;
    } else {
      data = (lista != null && !lista.isEmpty()) ? lista.get(0) : null;
    }

    return new ResultadoSP(
        (r.get("pRespuesta") != null) ? ((Number) r.get("pRespuesta")).intValue() : 0,
        (String) r.get("pMensaje"),
        data
    );
  }
}