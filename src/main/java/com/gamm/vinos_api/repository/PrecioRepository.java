package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.PrecioSucursal;
import com.gamm.vinos_api.dto.view.PrecioView;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface PrecioRepository {
  ResultadoSP asignarPrecio(PrecioSucursal precio);

  // Lista para visualizar todos los precios de stock (admin)
  List<PrecioView> listarTotalPreciosStock();

  // Lista para mostrar los precios de cada sucursal (vendedor)
  List<PrecioView> listarPreciosStockSucursal(Integer idSucursal);

  // Filtro para ver por nombre de vino o idSucursal (admin)
  ResultadoSP filtrarPorVinoOSucursal(String nombreVino, Integer idSucursal);

  // Lista para visualizar los detalles de los precios de un determinado vino
  List<PrecioView> listarPreciosDetalle(Integer idVino , Integer idSucursal);

  // Filtro para mostrar por nombre de vino de cada sucursal (vendedor)
  ResultadoSP filtrarPorVino(String nombreVino, Integer idSucursal);

  String obtenerNombreVino(Integer idVino);
}
