package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.NotificacionDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificacionRowMapper implements RowMapper<NotificacionDTO> {
  @Override
  public NotificacionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    NotificacionDTO dto = new NotificacionDTO();
    dto.setIdNotificacion(rs.getInt("idNotificacion"));
    dto.setTipo(rs.getString("tipo"));
    dto.setTitulo(rs.getString("titulo"));
    dto.setMensaje(rs.getString("mensaje"));
    dto.setRuta(rs.getString("ruta"));
    dto.setLeida(rs.getBoolean("leida"));
    dto.setFechaCreacion(
        rs.getTimestamp("fechaCreacion") != null
            ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
            : null
    );
    return dto;
  }
}
