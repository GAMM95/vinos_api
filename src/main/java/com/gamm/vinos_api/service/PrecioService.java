package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.dto.view.PrecioDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface PrecioService {
  ResultadoSP asignarPrecio (PrecioSucursal precio);

  List<PrecioDTO> listarTotalPreciosStock();

  List<PrecioDTO> listarPreciosStockSucursal();

  ResultadoSP filtrarPorVinoOSucursal(String nombreVino, Integer idSucursal);

  List<PrecioDTO> listarPreciosDetalle(Integer idVino, Integer idSucursal);

  ResultadoSP filtrarPorVino(String nombreVino);
}
