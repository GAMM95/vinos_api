package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.queries.*;

import java.util.List;

public interface DashboardRepository {

  IngresoVentasUserDTO getIngresoVentasUser(Integer idUsuario);

  CantidadVentasUserDTO getCantidadVentasUser(Integer idUsuario);

  VentasLitrosUserDTO getVentasLitros(Integer idUsuario);

  List<IngresoVentasAnualUserDTO> getIngresoVentasAnualesUser(Integer idUsuario);

  List<InversionComprasAnualUserDTO> getInversionComprasAnualesUser(Integer idUsuario);

  List<BalanceNetoMensualUserDTO> getBalanceNetoMensualUser(Integer idUsuario);

  List<CantidadComprasMensualUserDTO> cantidadComprasAnuales(Integer idUsuario);

  List<CantidadVentasMensualUserDTO> cantidadVentasAnuales(Integer idUsuario);

  List <StockDashboardUserDTO> getStockDashboard(Integer idSucursal);

  VinoMasVendidoUserDTO getVinoMasVendido(Integer idUsuario);

  VentaResumenAdminDTO getVentaResumenAdmin();

  VentasTotalAdminDTO getVentasTotalAdmin();

  LitrosVendidosAdminDTO getVentasLitrosAdmin();

  List<StockLitrosSucursalDTO> getStockLitrosTotalesSucursal();

  List<StockVinoSucursalDTO> getStockVinoSucursal();

  StockLitrosAlmacenDTO getTotalLitrosAlmacen();

  List<CantidadVentasMensualAdminDTO> getCantidadVentasMensualAdmin();

  List<CantidadComprasMensualAdminDTO> getCantidadComprasMensualAdmin();

  List<BalanceNetoDiarioDTO> getBalanceNetoDiario();

  BalanceGlobalDTO getBalanceGlobal();
}
