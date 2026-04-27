package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.properties.EmpresaProperties;
import com.gamm.vinos_api.config.properties.NumeroALetras;
import com.gamm.vinos_api.dto.queries.ComprobanteDTO;
import com.gamm.vinos_api.dto.request.ResultadoComprobante;
import com.gamm.vinos_api.repository.ComprobanteRepository;
import com.gamm.vinos_api.service.ComprobanteService;
import com.gamm.vinos_api.service.SunatService;
import com.gamm.vinos_api.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComprobanteServiceImpl extends BaseService implements ComprobanteService {

  // ─── Templates ────────────────────────────────────────────────────────────
  private static final String TEMPLATE_A4 = "reports/comprobante_venta.jrxml";
  private static final String TEMPLATE_TICKET = "reports/ticket_venta.jrxml";
  private final ComprobanteRepository comprobanteRepository;
  private final EmpresaProperties empresaProperties;
  private final SunatService sunatService;
  // Ruta base donde se guardan los PDF en disco
  @Value("${comprobantes.ruta-almacenamiento:comprobantes}")
  private String rutaAlmacenamiento;

  // ─── Generar PDF A4 ───────────────────────────────────────────────────────

  @Override
  public byte[] generarPdfPorVenta(Integer idVenta) {
    List<ComprobanteDTO> filas = comprobanteRepository.obtenerComprobantePorVenta(idVenta);
    return buildPdf(filas, TEMPLATE_A4);
  }

  @Override
  public byte[] generarPdfPorComprobante(Integer idComprobante) {
    List<ComprobanteDTO> filas = comprobanteRepository.obtenerComprobantePorId(idComprobante);
    return buildPdf(filas, TEMPLATE_A4);
  }

  // ─── Generar Ticket ───────────────────────────────────────────────────────

  @Override
  public byte[] generarTicketPorVenta(Integer idVenta) {
    List<ComprobanteDTO> filas = comprobanteRepository.obtenerComprobantePorVenta(idVenta);
    return buildPdf(filas, TEMPLATE_TICKET);
  }

  @Override
  public byte[] generarTicketPorComprobante(Integer idComprobante) {
    List<ComprobanteDTO> filas = comprobanteRepository.obtenerComprobantePorId(idComprobante);
    return buildPdf(filas, TEMPLATE_TICKET);
  }

  // ─── Genera AMBOS y guarda el A4 en disco ────────────────────────────────

  @Override
  public ResultadoComprobante generarAmbos(Integer idVenta) {

    log.info("[PDF] Generando comprobante para venta: {}", idVenta);

    List<ComprobanteDTO> filas = comprobanteRepository.obtenerComprobantePorVenta(idVenta);

    log.info("[PDF] Filas obtenidas: {}", filas.size());

    if (filas.isEmpty()) {
      log.error("[PDF] No hay datos para la venta {}", idVenta);
      throw new IllegalArgumentException(
          "No se encontró comprobante para la venta: " + idVenta);
    }

    ComprobanteDTO cabecera = filas.getFirst();
    log.info("[PDF] Número comprobante: {}", cabecera.getNumeroComprobante());

    byte[] pdfA4 = buildPdf(filas, TEMPLATE_A4);
    byte[] pdfTicket = buildPdf(filas, TEMPLATE_TICKET);

    log.info("[PDF] PDFs generados correctamente");

    guardarEnDisco(pdfA4, cabecera.getNumeroComprobante(), "comprobante");
    guardarEnDisco(pdfTicket, cabecera.getNumeroComprobante(), "ticket");

    String rutaPdf = construirRuta(cabecera.getNumeroComprobante(), "comprobante");

    comprobanteRepository.guardarRutaPdf(cabecera.getIdComprobante(), rutaPdf);

    return new ResultadoComprobante(
        pdfA4,
        pdfTicket,
        cabecera.getNumeroComprobante(),
        cabecera.getIdComprobante()
    );
  }

  // ─── Lógica interna PDF ───────────────────────────────────────────────────

  private byte[] buildPdf(List<ComprobanteDTO> filas, String rutaTemplate) {
    log.info("[PDF] Iniciando generación con template: {}", rutaTemplate);
    if (filas == null || filas.isEmpty()) {
      log.error("[PDF] Lista de comprobante vacía");
      throw new IllegalArgumentException("No se encontró el comprobante.");
    }
    try {
      ComprobanteDTO cabecera = filas.getFirst();
      ClassPathResource resource = new ClassPathResource(rutaTemplate);

      log.info("[PDF] Verificando existencia del template...");
      log.info("[PDF] Exists: {}", resource.exists());

      if (!resource.exists()) {
        throw new RuntimeException("Template no encontrado: " + rutaTemplate);
      }

      InputStream reportStream = new ClassPathResource(rutaTemplate).getInputStream();
      InputStream is = getClass().getClassLoader()
          .getResourceAsStream("reports/comprobante_venta.jrxml");

      if (is == null) {
        throw new RuntimeException("NO SE ENCONTRÓ EL JRXML");
      }

      log.info("[PDF] Compilando reporte...");
      JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

      log.info("[PDF] Construyendo parámetros...");
      Map<String, Object> params = buildParams(cabecera);

      log.info("[PDF] Total registros: {}", filas.size());

      JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(filas);

      log.info("[PDF] Llenando reporte...");
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

      log.info("[PDF] Exportando a PDF...");
      return JasperExportManager.exportReportToPdf(jasperPrint);

    } catch (Exception e) {
      log.error("Error generando PDF [{}]: {}", rutaTemplate, e.getMessage(), e);
      throw new RuntimeException("Error al generar el PDF: " + rutaTemplate, e);
    }
  }

  // Extraído para tener los params en un solo lugar
  private Map<String, Object> buildParams(ComprobanteDTO cabecera) throws IOException {
    Map<String, Object> params = new HashMap<>();

    // Empresa (desde EmpresaProperties)
    InputStream logoStream = new ClassPathResource("assets/logo_viñaCascasDark.png").getInputStream();
    params.put("logo", logoStream);
    params.put("empresaNombre", empresaProperties.getNombre());
    params.put("empresaRuc", empresaProperties.getRuc());
    params.put("empresaTelefono", empresaProperties.getTelefono());
    params.put("empresaDireccion", empresaProperties.getDireccion());

    // Comprobante
    params.put("numeroComprobante", cabecera.getNumeroComprobante());
    params.put("tipoComprobante", cabecera.getTipoComprobante());

    LocalDateTime fechaEmision = cabecera.getFechaEmision();
    params.put("fecha", fechaEmision.toLocalDate().toString());
    params.put("hora", fechaEmision.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));  // 18:45
    params.put("estadoSunat", cabecera.getEstadoSunat());
    params.put("fechaEmision", fechaEmision.toString()); // para el A4

    // Venta
    params.put("codVenta", cabecera.getCodVenta());
    params.put("metodoPago", cabecera.getMetodoPago());
    params.put("descuento", cabecera.getDescuento());
    params.put("total", cabecera.getTotal());
    params.put ("totalEnLetras", NumeroALetras.convertir(cabecera.getTotal()));
    // Sucursal
    params.put("sucursalNombre", cabecera.getNombreSucursal());
    params.put("sucursalDireccion", cabecera.getDireccionSucursal());

    // Personas
    params.put("vendedor", cabecera.getVendedor());
    params.put("clienteNombre", cabecera.getClienteNombre());
    params.put("clienteCelular", cabecera.getClienteCelular());
    params.put("clienteDireccion", cabecera.getClienteDireccion());

    return params;
  }

  // ─── Persistencia en disco ────────────────────────────────────────────────

  private void guardarEnDisco(byte[] pdf, String numeroComprobante, String tipo) {
    try {
      String ruta = construirRuta(numeroComprobante, tipo);
      log.info("[PDF] Guardando {} en ruta: {}", tipo, ruta);

      Path path = Paths.get(ruta);
      Files.createDirectories(path.getParent());
      Files.write(path, pdf);

      log.info("[PDF] {} guardado correctamente", tipo);

    } catch (IOException e) {
      log.error("[PDF] Error guardando {}: {}", tipo, e.getMessage(), e);
    }
  }

  private String construirRuta(String numeroComprobante, String tipo) {
    // Ejemplo: comprobantes/2025/04/comprobante_B001-00000001.pdf
    //          comprobantes/2025/04/ticket_B001-00000001.pdf
    LocalDateTime ahora = LocalDateTime.now();
    String carpeta = String.format("%s/%d/%02d",
        rutaAlmacenamiento,
        ahora.getYear(),
        ahora.getMonthValue());

    String nombreArchivo = tipo + "_" +
        numeroComprobante.replace("-", "_") + ".pdf";

    return carpeta + "/" + nombreArchivo;
  }

  // ─── SUNAT stub ───────────────────────────────────────────────────────────

  @Async
  @Override
  public void enviarASunat(Integer idComprobante) {
    List<ComprobanteDTO> filas = comprobanteRepository.obtenerComprobantePorId(idComprobante);
    if (filas.isEmpty()) {
      log.warn("[SUNAT] Comprobante {} no encontrado.", idComprobante);
      return;
    }
    ComprobanteDTO cabecera = filas.getFirst();
    // SunatService decide internamente si está activo o no
    boolean aceptado = sunatService.enviar(cabecera, filas);
    String nuevoEstado = aceptado ? "Aceptado" : "Pendiente";
    comprobanteRepository.actualizarEstadoSunat(idComprobante, nuevoEstado);
  }

}