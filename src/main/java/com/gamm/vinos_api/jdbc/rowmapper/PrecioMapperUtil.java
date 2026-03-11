package com.gamm.vinos_api.jdbc.rowmapper;


import com.gamm.vinos_api.domain.view.PrecioView;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PrecioMapperUtil {

  // Campos para los precios del stock
  public static void mapPrecioStockBase (PrecioView pv, ResultSet rs) throws SQLException {
    pv.setIdPrecio(rs.getInt("idPrecio"));
    pv.setIdVino(rs.getInt("idVino"));
    pv.setNombreVino(rs.getString("nombreVino"));
    pv.setIdSucursal(rs.getInt("idSucursal"));
    pv.setNombreSucursal(rs.getString("nombreSucursal"));
    pv.setOrigen(rs.getString("origen"));
    pv.setPrecioVenta(rs.getBigDecimal("precioVenta"));
  }

  // Campos base para el detalle de precios del stock por vino
  public static void mapPrecioDetallado(PrecioView v, ResultSet rs) throws SQLException {
    Timestamp tsInicio = rs.getTimestamp("fechaInicio");
    Timestamp tsFin = rs.getTimestamp("fechaFin");

    v.setFechaInicio(tsInicio != null ? tsInicio.toLocalDateTime() : null);
    v.setFechaFin(tsFin != null ? tsFin.toLocalDateTime() : null);

    v.setEstado(rs.getString("estado"));
  }
}
