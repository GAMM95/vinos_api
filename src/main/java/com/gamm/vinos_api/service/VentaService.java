package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.CarritoVentaDTO;
import com.gamm.vinos_api.dto.view.DetalleVentaDTO;
import com.gamm.vinos_api.dto.view.VentaDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VentaService {
  List<CarritoVentaDTO> listarCarritoVentaUsuario();

  List<CarritoVentaDTO> listarCarritoVentaAdmin(Integer idVenta);

  ResultadoSP agregarCarritoVenta(Venta venta);

  ResultadoSP confirmarVenta(Integer idVenta, String metodoPago, BigDecimal descuento);

  ResultadoSP retirarProductoCarrito(Integer idVenta, Integer idVino);

  PaginaResultado<VentaDTO> listarVentasUsuario(int pagina, int limite);

  PaginaResultado<VentaDTO> listarTotalVenta(int pagina, int limite);

  List<DetalleVentaDTO> listarDetalleVenta(Integer idVenta);

  ResultadoSP anularVenta(Integer idVenta);

  PaginaResultado<VentaDTO> filtrarMisVentasPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  PaginaResultado<VentaDTO> filtrarVentasPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);
}

