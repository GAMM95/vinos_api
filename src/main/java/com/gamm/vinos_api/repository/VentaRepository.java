package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaView;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface VentaRepository {
  List<CarritoVentaView> listarCarritoVentaUsuario(Integer idUsuario);

  List<CarritoVentaView> listarCarritoVentaAdmin(Integer idVenta);

  // Agregar productos al carrito
  ResultadoSP agregarProductoCarrito(Integer idUsuario, Venta venta, DetalleVenta detalle);
}
