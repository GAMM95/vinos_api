package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.properties.EmpresaProperties;
import com.gamm.vinos_api.domain.enums.ReporteTemplate;
import com.gamm.vinos_api.service.ReporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private final EmpresaProperties empresaProperties;
  // Cache de reportes compilados (clave = path del jrxml)
  private final Map<String, JasperReport> cache = new ConcurrentHashMap<>();


  // ─── Parámetros comunes que siempre se inyectan en todos los reportes ─

  private Map<String, Object> paramsBase() {
    Map<String, Object> params = new HashMap<>();

    try {
      InputStream logoStream =
          new ClassPathResource("assets/logo_viñaCascasDark.png").getInputStream();

      params.put("logo", logoStream);

    } catch (Exception e) {
      log.error("Error cargando logo", e);
      throw new RuntimeException("No se pudo cargar el logo", e);
    }

    params.put("empresaNombre", empresaProperties.getNombre());
    params.put("empresaRuc", empresaProperties.getRuc());
    params.put("empresaTelefono", empresaProperties.getTelefono());
    params.put("empresaDireccion", empresaProperties.getDireccion());
    params.put("fechaGeneracion", LocalDate.now().format(FMT));

    return params;
  }

  // Cache de reportes para evitar recompilar
  private JasperReport getReport(String path) {
    return cache.computeIfAbsent(path, p -> {
      try (InputStream stream = new ClassPathResource(p).getInputStream()) {
        log.info("Compilando reporte: {}", p);
        return JasperCompileManager.compileReport(stream);
      } catch (Exception e) {
        log.error("Error compilando reporte: {}", p, e);
        throw new RuntimeException("Error compilando reporte: " + p, e);
      }
    });
  }

  @Override
  public byte[] generarPdf(ReporteTemplate template, Map<String, Object> params, List<?> data) {
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data != null ? data : List.of());
    return generarPdf(template, params, dataSource);
  }


  @Override
  public byte[] generarPdf(ReporteTemplate template, Map<String, Object> params, JRDataSource dataSource) {
    if (template == null) throw new IllegalArgumentException("Template no puede ser null");
    if (dataSource == null) throw new IllegalArgumentException("DataSource no puede ser null");

    // Mezclar params base + params específicos del reporte
    // Los params específicos tienen prioridad (pueden sobreescribir base)
    Map<String, Object> merged = new HashMap<>(paramsBase());

    if (params != null && !params.isEmpty()) merged.putAll(params);

    try {
      JasperReport jasperReport = getReport(template.getPath());
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, merged, dataSource);
      return JasperExportManager.exportReportToPdf(jasperPrint);

    } catch (Exception e) {
      log.error("Error generando reporte [{}]: {}", template.getPath(), e.getMessage(), e);
      throw new RuntimeException("Error al generar el reporte: " + template.name(), e);
    }
  }
}
