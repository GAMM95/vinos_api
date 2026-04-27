package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.service.ComprobanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comprobantes", description = "Generación de comprobantes de venta")
@RestController
@RequestMapping("/api/v1/comprobantes")
@RequiredArgsConstructor
public class ComprobanteController extends AbstractRestController{
  private final ComprobanteService comprobanteService;

  // Descargaar comprobante A4 guardado
  @Operation(summary = "Descargar comprobante A4 por idVenta")
  @GetMapping("/venta/{idVenta}/pdf")
  public ResponseEntity<byte[]> descargarPorVenta(@PathVariable Integer idVenta) {
    byte[] pdf = comprobanteService.generarPdfPorVenta(idVenta);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=comprobante-venta-" + idVenta + ".pdf")
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }

  // Re-imprimir ticket si falló la impresión original
  @Operation(summary = "Re-imprimir ticket por idVenta")
  @GetMapping("/venta/{idVenta}/ticket")
  public ResponseEntity<byte[]> reimprimirTicket(@PathVariable Integer idVenta) {
    byte[] pdf = comprobanteService.generarTicketPorVenta(idVenta);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "inline; filename=ticket-" + idVenta + ".pdf") // inline → abre en navegador
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }

  // Descargar A4 por idComprobante
  @Operation(summary = "Descargar comprobante A4 por idComprobante")
  @GetMapping("/{idComprobante}/pdf")
  public ResponseEntity<byte[]> descargarA4(@PathVariable Integer idComprobante) {
    byte[] pdf = comprobanteService.generarPdfPorComprobante(idComprobante);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=comprobante-" + idComprobante + ".pdf")
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }

  // Re-imprimir ticket por idComprobante
  @Operation(summary = "Re-imprimir ticket por idComprobante")
  @GetMapping("/{idComprobante}/ticket")
  public ResponseEntity<byte[]> reimprimirTicketPorComprobante(
      @PathVariable Integer idComprobante) {
    byte[] pdf = comprobanteService.generarTicketPorComprobante(idComprobante);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "inline; filename=ticket-" + idComprobante + ".pdf")
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }
}
