package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.properties.SunatProperties;
import com.gamm.vinos_api.dto.queries.ComprobanteDTO;
import com.gamm.vinos_api.service.SunatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SunatServiceImpl implements SunatService {
  private final SunatProperties sunatProperties;


  @Override
  public boolean enviar(ComprobanteDTO cabecera, List<ComprobanteDTO> detalles) {
    // ── Switch principal: si SUNAT no está activo, no hace nada ──────────
    if (!sunatProperties.isActivo()) {
      log.info("[SUNAT] Inactivo — comprobante {} omitido.",
          cabecera.getNumeroComprobante());
      return false;
    }

    try {
      log.info("[SUNAT] Enviando comprobante {}...",
          cabecera.getNumeroComprobante());

      /*
       * ── ACTIVAR CUANDO SE INTEGRE SUNAT ──────────────────────────
       *
       * // 1. Cargar certificado .pfx
       * X509Certificate cert = CertificateUtils.getCertificate(
       *     sunatProperties.getCertificado(),
       *     sunatProperties.getClaveCertificado()
       * );
       * PrivateKey key = CertificateUtils.getPrivateKey(
       *     sunatProperties.getCertificado(),
       *     sunatProperties.getClaveCertificado()
       * );
       *
       * // 2. Construir la boleta con Greenter
       * Boleta boleta = buildBoleta(cabecera, detalles);
       *
       * // 3. Configurar See (motor de Greenter)
       * See see = new SeeBuilder()
       *     .withService(new BillServiceXml())
       *     .withEndpoint(sunatProperties.getUrl().getBoleta())
       *     .withXmlSigner(new XmlSigner()
       *         .setCertificate(cert)
       *         .setPrivateKey(key))
       *     .build();
       *
       * // 4. Enviar
       * BillResult result = see.send(boleta);
       *
       * log.info("[SUNAT] Comprobante {} → {}",
       *     cabecera.getNumeroComprobante(),
       *     result.isSuccess() ? "Aceptado" : "Rechazado");
       *
       * return result.isSuccess();
       *
       * ─────────────────────────────────────────────────────────────
       */

      return false; // reemplazar por result.isSuccess() al activar

    } catch (Exception e) {
      log.error("[SUNAT] Error al enviar comprobante {}: {}",
          cabecera.getNumeroComprobante(), e.getMessage(), e);
      return false;
    }
  }
}
