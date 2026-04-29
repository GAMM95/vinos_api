package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.enums.ReporteTemplate;
import net.sf.jasperreports.engine.JRDataSource;

import java.util.List;
import java.util.Map;

public interface ReporteService {
  /**
   * Genera un PDF a partir de una lista de objetos (JRBeanCollectionDataSource).
   * Uso más común: pasar List<MiDTO>
   */
  byte[] generarPdf(ReporteTemplate template,
                    Map<String, Object> params,
                    List<?> data);

  /**
   * Genera un PDF con un JRDataSource ya construido.
   * Útil cuando necesitas JREmptyDataSource o un dataSource personalizado.
   */
  byte[] generarPdf(ReporteTemplate template,
                    Map<String, Object> params,
                    JRDataSource dataSource);
}
