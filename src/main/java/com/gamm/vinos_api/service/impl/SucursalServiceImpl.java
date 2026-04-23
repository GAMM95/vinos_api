package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.SucursalDTO;
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
  public List<SucursalDTO> listarSucursales() {
    return sucursalRepository.listarSucursales();
  }

  @Override
  public ResultadoSP registrarSucursal(Sucursal sucursal) {
    ResultadoSP resultado = sucursalRepository.registrarSucursal(sucursal);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP actualizarSucursal(Sucursal sucursal) {
    ResultadoSP resultado = sucursalRepository.actualizarSucursal(sucursal);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP darDeBajaSucursal(Integer idSucursal) {
    ResultadoSP resultado = sucursalRepository.darDeBajaSucursal(idSucursal);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP darDeAltaSucursal(Integer idSucursal) {
    ResultadoSP resultado = sucursalRepository.darDeAltaSucursal(idSucursal);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP filtrarSucursal(String nombreSucursal) {
    return sucursalRepository.filtrarSucursal(nombreSucursal);
  }
}
