package com.gamm.vinos_api.jdbc.rowmapper.utils;

import com.gamm.vinos_api.dto.view.AlmacenDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlmacenMapperUtil {

  // Campos base para AlmacenDTO (campos comunes en todas las vistas)
  public static void mapAlmacenBase(AlmacenDTO v, ResultSet rs) throws SQLException {
    v.setOrigen(rs.getString("origen"));
    v.setTotalLitros(rs.getBigDecimal("totalLitros"));
  }

  // Para la vista detallada del stock del almacen
  public static void mapDetallado(AlmacenDTO v, ResultSet rs) throws SQLException {
    v.setNombreVino(rs.getString("nombreVino"));
    v.setPresentacion(rs.getString("presentacion"));
    v.setTotalUnidades(rs.getInt("totalUnidades"));
  }

  // Para la vista del stock por vino
  public static void mapPorVino(AlmacenDTO v, ResultSet rs) throws SQLException {
    v.setIdVino(rs.getInt("idVino"));
    v.setNombreVino(rs.getString("nombreVino"));
  }

  public static void mapDistribucion (AlmacenDTO v, ResultSet rs) throws SQLException {
    v.setIdAlmacen(rs.getInt("idAlmacen"));
    v.setIdCatalogo(rs.getInt("idCatalogo"));
    v.setNombreVino(rs.getString("nombreVino"));
    v.setOrigen(rs.getString("origen"));
    v.setPresentacion(rs.getString("presentacion"));
    v.setTotalUnidades(rs.getInt("totalUnidades"));
    v.setTotalLitros(rs.getBigDecimal("totalLitros"));
    v.setFechaDistribucion(rs.getTimestamp("fechaDistribucion").toLocalDateTime().toLocalDate());
  }

}
