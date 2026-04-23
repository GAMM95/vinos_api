package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import com.gamm.vinos_api.domain.enums.Rol;
import com.gamm.vinos_api.domain.model.Persona;
import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.view.UsuarioDTO;
import com.gamm.vinos_api.jdbc.base.BaseUsuarioSPRepository;
import com.gamm.vinos_api.jdbc.rowmapper.UsuarioViewRowMapper;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.util.ResultadoSP;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
  public ResultadoSP activarUsuario(Integer idUsuario, Integer idSucursal) {
    Usuario u = new Usuario();
    u.setIdUsuario(idUsuario);
    if (idSucursal != null) {
      u.setSucursal(new Sucursal());
      u.getSucursal().setIdSucursal(idSucursal);
    }
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
  public List<UsuarioDTO> listarUsuarios() {
    return jdbcTemplate.query(VIEW_USUARIOS, new UsuarioViewRowMapper());
  }

  @Override
  public List<UsuarioDTO> listarUsuariosPaginados(int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    String sql = VIEW_USUARIOS + " LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, new UsuarioViewRowMapper(), limite, offset);
  }

  @Override
  public Long contarUsuarios() {
    String sql = "SELECT  COUNT(*) FROM vw_usuarios";
    return jdbcTemplate.queryForObject(sql, Long.class);
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

    ResultadoSP resultado = ejecutarSPConLista(construirParametros(10, u), false);

    if (!(resultado.getData() instanceof UsuarioDTO uv)) return null;

    return mapUsuarioViewToUsuario(uv);
  }

  @Override
  public ResultadoSP verificarUsername(String username, Integer idUsuario) {
    Usuario u = new Usuario();
    u.setUsername(username);
    u.setIdUsuario(idUsuario);

    return ejecutarSP(construirParametros(11, u));
  }

  @Override
  public Usuario obtenerUsuarioPorEmail(String email) {
    String sql = """
        SELECT idUsuario, email, nombres, apellidoPaterno, apellidoMaterno
        FROM vw_usuarios
        WHERE email = ?
        LIMIT 1
        """;

    return jdbcTemplate.query(sql, rs -> {
      if (!rs.next()) return null;

      // Construimos la persona
      Persona persona = new Persona();
      persona.setEmail(rs.getString("email"));
      persona.setNombres(rs.getString("nombres"));
      persona.setApellidoPaterno(rs.getString("apellidoPaterno"));
      persona.setApellidoMaterno(rs.getString("apellidoMaterno"));

      // Construimos el usuario
      Usuario usuario = new Usuario();
      usuario.setIdUsuario(rs.getInt("idUsuario"));
      usuario.setPersona(persona);

      return usuario;
    }, email);
  }

  @Override
  public List<UsuarioDTO> listarPorRolYSucursal(String rol, Integer idSucursal) {
    String sql = VIEW_USUARIOS + " WHERE rol = ? AND idSucursal = ?";
    return jdbcTemplate.query(sql, new UsuarioViewRowMapper(), rol, idSucursal);
  }

  @Override
  public UsuarioDTO obtenerUsuarioPorId(Integer idUsuario) {
   String sql = VIEW_USUARIOS + " WHERE idUsuario = ?";
    return jdbcTemplate.queryForObject(sql, new UsuarioViewRowMapper(), idUsuario);
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


  private Usuario mapUsuarioViewToUsuario(UsuarioDTO uv) {
    Persona persona = new Persona();
    persona.setIdPersona(uv.getIdPersona());
    persona.setNombres(uv.getNombres());
    persona.setApellidoPaterno(uv.getApellidoPaterno());
    persona.setApellidoMaterno(uv.getApellidoMaterno());
    persona.setCelular(uv.getCelular());
    persona.setEmail(uv.getEmail());
    persona.setDomicilio(uv.getDomicilio());

    Usuario usuario = new Usuario();
    usuario.setIdUsuario(uv.getIdUsuario());
    usuario.setUsername(uv.getUsername());
    usuario.setRutaFoto(uv.getRutaFoto());
    usuario.setRol(Rol.valueOf(uv.getRol().toUpperCase()));
    usuario.setEstado(EstadoRegistro.valueOf(uv.getEstado().toUpperCase()));
    usuario.setPersona(persona);

    return usuario;
  }

}
