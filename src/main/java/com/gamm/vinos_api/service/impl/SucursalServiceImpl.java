package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.dto.view.SucursalView;
import com.gamm.vinos_api.repository.SucursalRepository;
import com.gamm.vinos_api.service.SucursalService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SucursalServiceImpl implements SucursalService {

  // Inyeccion de dependencia al repositorio Sucursal
  private final SucursalRepository sucursalRepository;

  @Override
  public List<SucursalView> listarSucursales() {
    return sucursalRepository.listarSucursales();
  }

  @Override
  public ResultadoSP registrarSucursal(Sucursal sucursal) {
    return sucursalRepository.registrarSucursal(sucursal);
  }

  @Override
  public ResultadoSP actualizarSucursal(Sucursal sucursal) {
    return sucursalRepository.actualizarSucursal(sucursal);
  }

  @Override
  public ResultadoSP darDeBajaSucursal(Integer idSucursal) {
    return sucursalRepository.darDeBajaSucursal(idSucursal);
  }

  @Override
  public ResultadoSP darDeAltaSucursal(Integer idSucursal) {
    return sucursalRepository.darDeAltaSucursal(idSucursal);
  }

  @Override
  public ResultadoSP filtrarSucursal(String nombreSucursal) {
    return sucursalRepository.filtrarSucursal(nombreSucursal);
  }
}
