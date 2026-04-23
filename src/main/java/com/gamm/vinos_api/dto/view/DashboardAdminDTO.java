package com.gamm.vinos_api.dto.view;

import com.gamm.vinos_api.dto.queries.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardAdminDTO {
  private VentaResumenAdminDTO resumen;
  private VentasTotalAdminDTO totales;
  private LitrosVendidosAdminDTO litrosVendidos;

  private List<StockLitrosSucursalDTO> stockLitrosSucursal;
  private List<StockVinoSucursalDTO> stockVinoSucursal;
  private StockLitrosAlmacenDTO stockLitrosAlmacen;
  private List<CantidadVentasMensualAdminDTO> cantidadVentasMensual;
  private List<CantidadComprasMensualAdminDTO> cantidadComprasMensual;
  private List<BalanceNetoDiarioDTO> balanceNetoDiario;
  private BalanceGlobalDTO balanceGlobal;
}
