package com.gamm.vinos_api.dto.view;

import com.gamm.vinos_api.dto.queries.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardUserDTO {
  private IngresoVentasUserDTO ingresoVentas;
  private CantidadVentasUserDTO cantidadVentas;
  private VentasLitrosUserDTO litros;
  private List<IngresoVentasAnualUserDTO> ingresoVentasAnuales;
  private List<InversionComprasAnualUserDTO> inversionComprasAnuales;
  private List<BalanceNetoMensualUserDTO> balanceNetoMensuales;
  private List<CantidadComprasMensualUserDTO> cantidadComprasAnuales;
  private List<CantidadVentasMensualUserDTO> cantidadVentasAnuales;
  private List<StockDashboardUserDTO> stock;
  private VinoMasVendidoUserDTO vinoMasVendido;
}
