package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.CarritoCompraDTO;
import com.gamm.vinos_api.dto.view.CompraDTO;
import com.gamm.vinos_api.dto.view.ProductosCarritoDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface CompraService {

  // ─── Carrito ──────────────────────────────────────────────────────────────
  ResultadoSP agregarProductoCarrito(Compra compra);

  ResultadoSP eliminarProductoCarrito(Integer idDetalleCompra);

  ResultadoSP actualizarCantidadProductoCarrito(Compra compra);

  long contarProductosCarritoUsuario();

  List<ProductosCarritoDTO> listarProductosCarritoUsuario();

  List<CarritoCompraDTO> listarCarritosCompra();


  // ─── Operaciones de compra ────────────────────────────────────────────────
  ResultadoSP confirmarCompra(Compra compra);

  ResultadoSP cerrarCompra(Integer idCompra);

  ResultadoSP deshacerCerrarCompra(Integer idCompra);

  ResultadoSP anularCompra(Integer idCompra);

  ResultadoSP revertirCompra(Integer idCompra);

  // ─── Consultas del usuario autenticado ────────────────────────────────────
  PaginaResultado<CompraDTO> listarComprasUsuario(int pagina, int limite);

  List<CompraDTO> listarDetalleCompraUsuario(Integer idCompra);   // List — no es paginado

  PaginaResultado<CompraDTO> filtrarMisComprasPorFechas(
      LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite
  );

  // ─── Administración ───────────────────────────────────────────────────────
  List<CompraDTO> listarDetalleCompraAdmin(Integer idCompra);     // List — no es paginado

  List<CompraDTO> listarComprasPendientes();

  PaginaResultado<CompraDTO> listarTotalCompras(int pagina, int limite);

  PaginaResultado<CompraDTO> listarComprasConfirmadas(int pagina, int limite);

  PaginaResultado<CompraDTO> listarComprasAnuladas(int pagina, int limite);

  PaginaResultado<CompraDTO> filtrarComprasPorUsuarioYFechas(
      Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin,
      int pagina, int limite
  );
}
