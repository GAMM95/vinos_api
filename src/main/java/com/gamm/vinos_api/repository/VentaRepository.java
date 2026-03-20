package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaView;
import com.gamm.vinos_api.dto.view.VentaView;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface VentaRepository {
  // Listar cada venta de usuario
  long contarVentasUsuario(Integer idUsuario);

  List<VentaView> listarVentasUsuario(Integer idUsuario);

//  List<CarritoVentaView> listarCarritoVentaUsuario(Integer idUsuario, int pagina, int limite);


  List<CarritoVentaView> listarCarritoVentaAdmin(Integer idVenta);

  // Agregar productos al carrito
  ResultadoSP agregarProductoCarrito(Integer idUsuario, Venta venta, DetalleVenta detalle);

  ResultadoSP confirmarVenta(Integer idUsuario, Integer idVenta, String metodoPago);

  ResultadoSP retirarProductoCarrito(Integer idUsuario, Integer idVenta, Integer idVino);


}
