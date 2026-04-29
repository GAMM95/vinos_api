package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.enums.ReporteTemplate;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.ReporteService;
import com.gamm.vinos_api.service.report.CompraReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reportes", description = "Reportes del sistema en JSON y PDF")
@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController extends AbstractRestController {

  private final CompraReporteService compraReporteService;

  @Operation(summary = "Listar todas las compras")
  @GetMapping("/compras")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarCompras() {
    return ok(compraReporteService.obtenerTodas());
  }

  @Operation(summary = "Listar todas las compras")
  @GetMapping("/compras/{idCompra}/detalle")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> detalleCompras(
      @PathVariable Integer idCompra
  ) {
    return ok(compraReporteService.obtenerDetalle(idCompra));
  }

  @Operation(summary = "Exportar todas las compras a PDF")
  @GetMapping("/compras/pdf")
  public ResponseEntity<byte[]> exportarComprasPdf() {
    return pdf(compraReporteService.exportarTodasPdf(),
        ReporteTemplate.COMPRAS_GENERAL);
  }

  @Operation(summary = "Exportar el detalle de compra a PDF")
  @GetMapping("/compras/detalle/{idCompra}/pdf")
  public ResponseEntity<byte[]> exportarDetalleComprasPdf(
      @PathVariable Integer idCompra
  ) {
    return pdf(compraReporteService.exportarDetallePdf(idCompra),
        ReporteTemplate.COMPRAS_DETALLE, String.valueOf(idCompra));
  }

  // ─── Helpers PDF ──────────────────────────────────────────────────────

  private ResponseEntity<byte[]> pdf(byte[] data, ReporteTemplate template) {
    return pdf(data, template, null);
  }

  private ResponseEntity<byte[]> pdf(byte[] data,
                                     ReporteTemplate template,
                                     String sufijo) {
    String filename = template.getNombreArchivo()
        + (sufijo != null ? "-" + sufijo : "")
        + ".pdf";

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + filename)
        .contentType(MediaType.APPLICATION_PDF)
        .body(data);
  }
}
