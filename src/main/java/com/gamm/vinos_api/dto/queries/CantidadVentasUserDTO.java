package com.gamm.vinos_api.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CantidadVentasUserDTO {
  private Integer idUsuario;
  private Integer totalVentasMesActual;
  private Integer totalVentasHistorico;
}
