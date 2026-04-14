package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.DashboardAdminView;
import com.gamm.vinos_api.dto.view.DashboardUserView;
import com.gamm.vinos_api.repository.DashboardRepository;
import com.gamm.vinos_api.service.BaseService;
import com.gamm.vinos_api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl extends BaseService implements DashboardService {

  private final DashboardRepository dashboardRepository;

  @Override
  public DashboardUserView getDashboardUser() {
    Integer idUsuario = getIdUsuarioAutenticado();
    Integer idSucursal = getIdSucursalAutenticada();
    DashboardUserView dto = new DashboardUserView();
    // Agregar datos
    dto.setIngresoVentas(dashboardRepository.getIngresoVentasUser(idUsuario));
    dto.setCantidadVentas(dashboardRepository.getCantidadVentasUser(idUsuario));
    dto.setLitros(dashboardRepository.getVentasLitros(idUsuario));
    dto.setIngresoVentasAnuales(dashboardRepository.getIngresoVentasAnualesUser(idUsuario));
    dto.setInversionComprasAnuales(dashboardRepository.getInversionComprasAnualesUser(idUsuario));
    dto.setBalanceNetoMensuales(dashboardRepository.getBalanceNetoMensualUser(idUsuario));
    dto.setCantidadComprasAnuales(dashboardRepository.cantidadComprasAnuales(idUsuario));
    dto.setCantidadVentasAnuales(dashboardRepository.cantidadVentasAnuales(idUsuario));
    dto.setStock(dashboardRepository.getStockDashboard(idSucursal));
    dto.setVinoMasVendido(dashboardRepository.getVinoMasVendido(idUsuario));
    return dto;
  }

  @Override
  public DashboardAdminView getDashboardAdmin() {
    DashboardAdminView dto = new DashboardAdminView();
    // Agregar datos
    dto.setResumen(dashboardRepository.getVentaResumenAdmin());
    dto.setTotales(dashboardRepository.getVentasTotalAdmin());
    dto.setLitrosVendidos(dashboardRepository.getVentasLitrosAdmin());
    dto.setStockLitrosSucursal(dashboardRepository.getStockLitrosTotalesSucursal());
    dto.setStockVinoSucursal(dashboardRepository.getStockVinoSucursal());
    dto.setStockLitrosAlmacen(dashboardRepository.getTotalLitrosAlmacen());
    dto.setCantidadVentasMensual(dashboardRepository.getCantidadVentasMensualAdmin());
    dto.setCantidadComprasMensual(dashboardRepository.getCantidadComprasMensualAdmin());
    dto.setBalanceNetoDiario(dashboardRepository.getBalanceNetoDiario());
    dto.setBalanceGlobal(dashboardRepository.getBalanceGlobal());
    return dto;
  }
}
