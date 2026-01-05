package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import com.gamm.vinos_api.domain.enums.Rol;
import com.gamm.vinos_api.domain.model.Persona;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.UsuarioView;
import com.gamm.vinos_api.jdbc.rowmapper.UsuarioViewRowMapper;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.utils.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioRepositoryImpl extends BaseUsuarioSPRepository implements UsuarioRepository {

  private static final String SP_USUARIO = "sp_usuario";
  private static final String VIEW_USUARIOS = "SELECT * FROM vw_usuarios";

  public UsuarioRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @PostConstruct
  private void init() {
    initCommon(SP_USUARIO, new UsuarioViewRowMapper());
  }

  @Override
  public ResultadoSP inactivarUsuario(Integer idUsuario) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);
    return ejecutarSP(construirParametros(3, u));
  }

  @Override
  public ResultadoSP activarUsuario(Integer idUsuario) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);
    return ejecutarSP(construirParametros(4, u));
  }

  @Override
  public ResultadoSP actualizarUsuario(Usuario usuario) {
    return ejecutarSP(construirParametros(6, usuario));
  }

  @Override
  public ResultadoSP filtrarUsuario(String terminoBusqueda) {
    Usuario u = new Usuario();
    u.setUsername(terminoBusqueda);
    return ejecutarSPConLista(construirParametros(5, u), true);
  }

  @Override
  public List<UsuarioView> listarUsuarios() {
    return jdbcTemplate.query(VIEW_USUARIOS, new UsuarioViewRowMapper());
  }

  @Override
  public ResultadoSP actualizarFoto(Integer idUsuario, String rutaFoto) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);
    u.setRutaFoto(rutaFoto);
    return ejecutarSP(construirParametros(9, u));
  }

  @Override
  public Usuario obtenerPorId(Integer idUsuario) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);

    ResultadoSP resultado =
        ejecutarSPConLista(construirParametros(10, u), false);

    if (resultado.getData() == null) return null;

    if (resultado.getData() instanceof UsuarioView uv) {

      Persona p = new Persona();
      p.setIdPersona(uv.getIdPersona());
      p.setNombres(uv.getNombres());
      p.setApellidoPaterno(uv.getApellidoPaterno());
      p.setApellidoMaterno(uv.getApellidoMaterno());
      p.setCelular(uv.getCelular());
      p.setDomicilio(uv.getDomicilio());

      Usuario user = new Usuario();
      user.setIdUsuario(uv.getIdUsuario());
      user.setUsername(uv.getUsername());
      user.setRutaFoto(uv.getRutaFoto());
      user.setRol(Rol.valueOf(uv.getRol().toUpperCase()));
      user.setEstado(EstadoRegistro.valueOf(uv.getEstado().toUpperCase()));
      user.setPersona(p);

      return user;
    }
    return null;
  }

  @Override
  public ResultadoSP verificarUsername(String username, Integer idUsuario) {
    Usuario u = new Usuario();
    u.setUsername(username);
    u.setIdUsuario(idUsuario);

    return ejecutarSP(construirParametros(11, u));
  }

  @Override
  public Usuario obtenerUsuarioConPassword(Integer idUsuario) {
    String sql = """
        SELECT idUsuario, username, password
        FROM usuario
        WHERE idUsuario = ?
        """;

    return jdbcTemplate.query(sql, rs -> {
      if (!rs.next()) return null;

      Usuario u = new Usuario();
      u.setIdUsuario(rs.getInt("idUsuario"));
      u.setUsername(rs.getString("username"));
      u.setPassword(rs.getString("password"));
      return u;
    }, idUsuario);
  }

}
