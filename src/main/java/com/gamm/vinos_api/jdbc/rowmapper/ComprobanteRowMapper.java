package com.gamm.vinos_api.jdbc.rowmapper;

import com.gamm.vinos_api.dto.queries.ComprobanteDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComprobanteRowMapper implements RowMapper<ComprobanteDTO> {
  @Override
  public ComprobanteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    ComprobanteDTO dto = new ComprobanteDTO();
    dto.setIdComprobante(rs.getInt("idComprobante"));
    dto.setSerie(rs.getString("serie"));
    dto.setNumero(rs.getInt("numero"));
    dto.setNumeroComprobante(rs.getString("numeroComprobante"));
    dto.setTipoComprobante(rs.getString("tipoComprobante"));
    dto.setFechaEmision(rs.getTimestamp("fechaEmision").toLocalDateTime());
    dto.setEstadoSunat(rs.getString("estadoSunat"));
    dto.setEstado(rs.getString("estado"));

    dto.setIdVenta(rs.getInt("idVenta"));
    dto.setCodVenta(rs.getString("codVenta"));
    dto.setMetodoPago(rs.getString("metodoPago"));
    dto.setDescuento(rs.getBigDecimal("descuento"));
    dto.setTotal(rs.getBigDecimal("total"));
    dto.setObservacion(rs.getString("observacion"));

    dto.setNombreSucursal(rs.getString("sucursalNombre"));
    dto.setDireccionSucursal(rs.getString("sucursalDireccion"));

    dto.setVendedor(rs.getString("vendedor"));

    dto.setClienteNombre(rs.getString("clienteNombre"));
    dto.setClienteCelular(rs.getString("clienteCelular"));
    dto.setClienteDireccion(rs.getString("clienteDireccion"));

    dto.setNombreVino(rs.getString("vinoNombre"));
    dto.setCantidadLitros(rs.getBigDecimal("cantidadLitros"));
    dto.setPrecioLitro(rs.getBigDecimal("precioLitro"));
    dto.setSubtotal(rs.getBigDecimal("subtotal"));

    return dto;
  }

}
