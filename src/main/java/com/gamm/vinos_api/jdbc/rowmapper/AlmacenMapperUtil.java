package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.domain.view.AlmacenView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlmacenMapperUtil {

  // Campos base para AlmacenView (campos comunes en todas las vistas)
  public static void mapAlmacenBase(AlmacenView v, ResultSet rs) throws SQLException {
    v.setOrigen(rs.getString("origen"));
    v.setTotalLitros(rs.getBigDecimal("totalLitros"));
  }

  // Para la vista detallada del stock del almacen
  public static void mapDetallado(AlmacenView v, ResultSet rs) throws SQLException {
    v.setIdCatalogo(rs.getInt("idCatalogo"));
    v.setNombreVino(rs.getString("nombreVino"));
    v.setPresentacion(rs.getString("presentacion"));
    v.setTotalUnidades(rs.getInt("totalUnidades"));
  }

  // Para la vista del stock por vino
  public static void mapPorVino(AlmacenView v, ResultSet rs) throws SQLException {
    v.setIdVino(rs.getInt("idVino"));
    v.setNombreVino(rs.getString("nombreVino"));
  }

}
