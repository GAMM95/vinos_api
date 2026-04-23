package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.domain.model.Notificacion;
import com.gamm.vinos_api.dto.queries.NotificacionDTO;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.NotificacionRowMapper;
import com.gamm.vinos_api.repository.NotificacionRepository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NotificacionRepositoryImpl extends SimpleJdbcDAOBase implements NotificacionRepository {

  private final NotificacionRowMapper rowMapper = new NotificacionRowMapper();

  public NotificacionRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public Notificacion guardarNotificacion(Notificacion n) {

    String sql = """
      INSERT INTO notificacion (idUsuarioDestino, rolDestino, tipo, titulo, mensaje, ruta) 
      VALUES (?,?,?,?,?,?)
      """;

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      var ps = connection.prepareStatement(sql, new String[] { "idNotificacion" });

      ps.setObject(1, n.getIdUsuarioDestino());
      ps.setObject(2, n.getRolDestino());
      ps.setString(3, n.getTipo());
      ps.setString(4, n.getTitulo());
      ps.setString(5, n.getMensaje());
      ps.setString(6, n.getRuta());

      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();

    if (key == null) {
      throw new IllegalStateException("No se generó ID para la notificación");
    }

    n.setIdNotificacion(key.intValue()); // 🔥 ESTE ES EL FIX REAL

    return n;
  }

  @Override
  public List<NotificacionDTO> encontrarMisNotificaciones(Integer idUsuario, String rol) {
    String sql = """
            SELECT * FROM notificacion
            WHERE idUsuarioDestino = ?
               OR (idUsuarioDestino IS NULL AND rolDestino = ?)
            ORDER BY fechaCreacion DESC
            LIMIT 20
        """;
    return jdbcTemplate.query(sql, rowMapper, idUsuario, rol);
  }

  @Override
  public long contarNoLeidas(Integer idUsuario, String rol) {
    String sql = """
            SELECT COUNT(*) FROM notificacion
            WHERE (idUsuarioDestino = ?
               OR (idUsuarioDestino IS NULL AND rolDestino = ?))
            AND leida = 0
        """;
    Long count = jdbcTemplate.queryForObject(sql, Long.class, idUsuario, rol);
    return count != null ? count : 0L;
  }

@Override
public int marcarLeida(Integer idNotificacion, Integer idUsuario, String rol) {
  String sql = """
        UPDATE notificacion SET leida = 1
        WHERE idNotificacion = ?
          AND (
            idUsuarioDestino = ?
            OR (idUsuarioDestino IS NULL AND rolDestino = ?)
          )
    """;
  return jdbcTemplate.update(sql, idNotificacion, idUsuario, rol);
}

  @Override
  public void marcarTodasLeidas(Integer idUsuario, String rol) {
    String sql = """
            UPDATE notificacion SET leida = 1
            WHERE (idUsuarioDestino = ?
               OR (idUsuarioDestino IS NULL AND rolDestino = ?))
            AND leida = 0
        """;
    jdbcTemplate.update(sql, idUsuario, rol);
  }
}
