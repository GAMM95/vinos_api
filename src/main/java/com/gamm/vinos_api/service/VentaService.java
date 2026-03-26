package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaView;
import com.gamm.vinos_api.util.ResultadoSP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VentaService {
  List<CarritoVentaView> listarCarritoVentaUsuario();

  List<CarritoVentaView> listarCarritoVentaAdmin(Integer idVenta);

  ResultadoSP agregarCarritoVenta(Venta venta);

  ResultadoSP confirmarVenta(Integer idVenta, String metodoPago, BigDecimal descuento);

  ResultadoSP retirarProductoCarrito(Integer idVenta, Integer idVino);
}

