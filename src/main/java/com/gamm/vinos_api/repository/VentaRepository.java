package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaDTO;
import com.gamm.vinos_api.dto.view.DetalleVentaDTO;
import com.gamm.vinos_api.dto.view.VentaDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VentaRepository {
  // Listar cada venta de usuario
  long contarVentasUsuario(Integer idUsuario);

  List<VentaDTO> listarVentasUsuario(Integer idUsuario, int pagina, int limite);

  // Listar todas las ventas
  long contarTotalVentas();

  VentaDTO obtenerVentaPorId(Integer idVenta);

  List<VentaDTO> listarTotalVentas(int pagina, int limite);

  // Listrar detalle de venta
  List<DetalleVentaDTO> listarDetalleVenta(Integer idVenta);

  List<CarritoVentaDTO> listarCarritoVentaUsuario(Integer idUsuario);

  List<CarritoVentaDTO> listarCarritoVentaAdmin(Integer idVenta);

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
