package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.domain.view.SucursalView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface SucursalService {
  List<SucursalView> listarSucursales();

  ResultadoSP registrarSucursal(Sucursal sucursal);

  ResultadoSP actualizarSucursal(Sucursal sucursal);

  ResultadoSP darDeBajaSucursal(Integer idSucursal);

  ResultadoSP darDeAltaSucursal(Integer idSucursal);

  ResultadoSP filtrarSucursal(String nombreSucursal);
}
