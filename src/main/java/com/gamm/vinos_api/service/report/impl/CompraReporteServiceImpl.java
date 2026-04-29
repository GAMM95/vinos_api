package com.gamm.vinos_api.service.report.impl;

import com.gamm.vinos_api.domain.enums.ReporteTemplate;
import com.gamm.vinos_api.dto.view.CompraDTO;
import com.gamm.vinos_api.repository.report.ReporteCompraRepository;
import com.gamm.vinos_api.service.ReporteService;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.service.report.CompraReporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompraReporteServiceImpl extends BaseService implements CompraReporteService {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private final ReporteCompraRepository reporteCompraRepository;
  private final ReporteService reporteService;

  // ─── JSON ─────────────────────────────────────────────────────────────

  @Override
  public List<CompraDTO> obtenerTodas() {
    return reporteCompraRepository.listarTodas();
  }

  @Override
  public List<CompraDTO> obtenerDetalle(Integer idCompra) {
    return reporteCompraRepository.listarDetalle(idCompra);
  }

  // ─── PDF ──────────────────────────────────────────────────────────────

  @Override
  public byte[] exportarTodasPdf() {
    return reporteService.generarPdf(
        ReporteTemplate.COMPRAS_GENERAL,
        Map.of("subtitulo", "Todas las compras"),
        reporteCompraRepository.listarTodas()
    );
  }

  @Override
  public byte[] exportarDetallePdf(Integer idCompra) {
    List<CompraDTO> datos = reporteCompraRepository.listarDetalle(idCompra);
    String codCompra = datos.isEmpty() ? String.valueOf(idCompra) : datos.getFirst().getCodCompra();
    return reporteService.generarPdf(
        ReporteTemplate.COMPRAS_DETALLE,
        Map.of("subtitulo", "Detalle de la compra: " + codCompra),
        datos
    );
  }

}
