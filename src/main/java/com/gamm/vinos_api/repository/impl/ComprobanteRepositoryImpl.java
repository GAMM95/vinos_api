package com.gamm.vinos_api.repository.impl;

import com.gamm.vinos_api.dto.queries.ComprobanteDTO;
import com.gamm.vinos_api.exception.handler.DatabaseExceptionHandler;
import com.gamm.vinos_api.jdbc.base.SimpleJdbcDAOBase;
import com.gamm.vinos_api.jdbc.rowmapper.ComprobanteRowMapper;
import com.gamm.vinos_api.repository.ComprobanteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class ComprobanteRepositoryImpl extends SimpleJdbcDAOBase implements ComprobanteRepository {

  // Consumo de la vista
  private static final String VW_COMPROBANTE = "SELECT * FROM vw_comprobante_venta";

  protected ComprobanteRepositoryImpl(DataSource dataSource) {
    super(dataSource);
  }

  // ─── Consultar comprobante por idVenta (desde la vista) ──────────────────

  @Override
  public List<ComprobanteDTO> obtenerComprobantePorVenta(Integer idVenta) {
    try {
      String sql = VW_COMPROBANTE + " WHERE idVenta = ?";
      return jdbcTemplate.query(sql, new ComprobanteRowMapper(), idVenta);
    } catch (Exception e) {
      log.error("Error al obtener comprovante por venta {}: {}", idVenta, e.getMessage());
      return Collections.emptyList();
    }
  }

  @Override
  public List<ComprobanteDTO> obtenerComprobantePorId(Integer idComprobante) {
    try {
      String sql = VW_COMPROBANTE + " WHERE idComprobante = ?";
      return jdbcTemplate.query(sql, new ComprobanteRowMapper(), idComprobante
      );
    } catch (Exception e) {
      log.error("Error al obtener comprobante {}: {}", idComprobante, e.getMessage());
      return Collections.emptyList();
    }
  }

  // ─── Para SUNAT: actualizar estadoSunat ──────────────────────────────────

  @Override
  public void actualizarEstadoSunat(Integer idComprobante, String estadoSunat) {
    String sql = "UPDATE comprobante_venta SET estadoSunat = ? WHERE idComprobante = ?";
    try {
      jdbcTemplate.update(sql, estadoSunat, idComprobante);
    } catch (Exception e) {
      log.error("Error al actualizar estadoSunat del comprobante {}: {}", idComprobante, e.getMessage());
    }
  }

  // ─── Para SUNAT: guardar ruta del PDF firmado ─────────────────────────────

  @Override
  public void guardarRutaPdf(Integer idComprobante, String rutaPdf) {
    String sql = "UPDATE comprobante_venta SET rutaPdf = ? WHERE idComprobante = ?";
    try {
      jdbcTemplate.update(sql, rutaPdf, idComprobante);
    } catch (Exception e) {
      log.error("Error al guardar rutaPdf del comprobante {}: {}", idComprobante, e.getMessage());
    }
  }
}
