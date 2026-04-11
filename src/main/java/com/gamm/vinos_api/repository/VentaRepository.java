package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaView;
import com.gamm.vinos_api.dto.view.DetalleVentaView;
import com.gamm.vinos_api.dto.view.VentaView;
import com.gamm.vinos_api.util.ResultadoSP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VentaRepository {
  // Listar cada venta de usuario
  long contarVentasUsuario(Integer idUsuario);

  List<VentaView> listarVentasUsuario(Integer idUsuario, int pagina, int limite);

  // Listar todas las ventas
  long contarTotalVentas();

  VentaView obtenerVentaPorId(Integer idVenta);

  List<VentaView> listarTotalVentas(int pagina, int limite);

  // Listrar detalle de venta
  List<DetalleVentaView> listarDetalleVenta(Integer idVenta);

  List<CarritoVentaView> listarCarritoVentaUsuario(Integer idUsuario);

  List<CarritoVentaView> listarCarritoVentaAdmin(Integer idVenta);

  // Agregar productos al carrito
  ResultadoSP agregarProductoCarrito(Integer idUsuario, Venta venta, DetalleVenta detalle);

  ResultadoSP confirmarVenta(Integer idUsuario, Integer idVenta, String metodoPago, BigDecimal descuento);

  ResultadoSP retirarProductoCarrito(Integer idUsuario, Integer idVenta, Integer idVino);

  ResultadoSP cancelarVenta(Integer idVenta);

  long contarVentasPorFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  ResultadoSP filtrarMisVentasPorFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  long contarVentasPorUsuarioOFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  ResultadoSP filtrarVentasPorUsuarioOFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

}
