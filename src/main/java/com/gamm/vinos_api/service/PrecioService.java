package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.domain.view.PrecioView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface PrecioService {
  ResultadoSP asignarPrecio (PrecioSucursal precio);

  List<PrecioView> listarTotalPreciosStock();

  List<PrecioView> listarPreciosStockSucursal();

  ResultadoSP filtrarPorVinoOSucursal(String nombreVino, Integer idSucursal);

  List<PrecioView> listarPreciosDetalle(Integer idVino, Integer idSucursal);

  ResultadoSP filtrarPorVino(String nombreVino);
}
