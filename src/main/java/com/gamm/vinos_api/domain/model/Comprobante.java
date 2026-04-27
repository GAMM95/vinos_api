package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoComprobante;
import com.gamm.vinos_api.domain.enums.EstadoSunat;
import com.gamm.vinos_api.domain.enums.TipoComprobante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comprobante {
  private Integer idComprobante;
  private Venta venta;
  private String serie;
  private Integer numero;
  private TipoComprobante tipoComprobante;
  private LocalDateTime fechaEmision;
  private String rutaPdf;
  private EstadoSunat estadoSunat = EstadoSunat.PENDIENTE; // por mientras hasta que se integre con sunat
  private EstadoComprobante estadoComprobante = EstadoComprobante.EMITIDO;
}