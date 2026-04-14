package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.enums.MetodoPago;
import com.gamm.vinos_api.domain.enums.OrigenVino;
import com.gamm.vinos_api.domain.enums.TipoFinanza;
import com.gamm.vinos_api.domain.enums.TipoVino;
import com.gamm.vinos_api.security.annotations.Publico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Tag(name = "Enumerados", description = "Valores de catálogo para formularios y filtros")
@RestController
@RequestMapping("/api/v1/enums")
@Publico
public class EnumController {

  @Operation(summary = "Listar tipos de vino")
  @GetMapping("/tipos-vino")
  public List<Map<String, String>> listarTipoVino() {
    return Arrays.stream(TipoVino.values())
        .map(t -> Map.of("codigo", t.name(), "descripcion", t.getValorBD()))
        .toList();
  }

  @Operation(summary = "Listar orígenes de vino")
  @GetMapping("/origenes-vino")
  public List<Map<String, String>> listarOrigenVino() {
    return Arrays.stream(OrigenVino.values())
        .map(o -> Map.of("codigo", o.name(), "descripcion", o.name()))
        .toList();
  }

  @Operation(summary = "Listar métodos de pago")
  @GetMapping("/metodos-pago")
  public List<Map<String, String>> listarMetodoPago() {
    return Arrays.stream(MetodoPago.values())
        .map(m -> Map.of("codigo", m.name(), "descripcion", formatear(m.name())))
        .toList();
  }

  @Operation(summary = "Listar tipos de finanza")
  @GetMapping("/tipos-finanza")
  public List<Map<String, String>> listarTipoFinanza() {
    return Arrays.stream(TipoFinanza.values())
        .map(t -> Map.of("codigo", t.name(), "descripcion", formatear(t.name())))
        .toList();
  }

  private String formatear(String valor) {
    return valor.substring(0, 1).toUpperCase() + valor.substring(1).toLowerCase();
  }
}
