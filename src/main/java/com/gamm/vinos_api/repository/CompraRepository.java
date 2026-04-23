package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.model.DetalleCompra;
import com.gamm.vinos_api.dto.view.CarritoCompraDTO;
import com.gamm.vinos_api.dto.view.CompraDTO;
import com.gamm.vinos_api.dto.view.ProductosCarritoDTO;
import com.gamm.vinos_api.util.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface CompraRepository {

  // Operaciones que modifican el carrito
  ResultadoSP agregarProductoCarrito(Integer idUsuario, Compra compra, DetalleCompra detalle);

  ResultadoSP eliminarProductoCarrito(Integer idUsuario, Integer idDetCompra);

  ResultadoSP actualizarCantidadProductoCarrito(Integer idUsuario, Compra compra, DetalleCompra detalle);

  ResultadoSP confirmarCompra(Integer idUsuario, Compra compra);

  // Operaciones que consultan el carrito
  long contarProductosCarrito(Integer idUsuario, Integer idCompra);

  List<ProductosCarritoDTO> listarProductosCarritos(Integer idUsuario, Integer idCompra);

  // Obtener carrito pendiente del usuario
  Compra obtenerCarritoPendiente(Integer idUsuario);

  // Listados
  List<CarritoCompraDTO> listarCarritosCompra();

  // Listar las compras realizadas de cada usuario
  long contarComprasUsuario(Integer idUsuario);

  List<CompraDTO> listarComprasUsuario(Integer idUsuario, int pagina, int limite);

  List<CompraDTO> listarDetalleComprasUsuario(Integer idUsuario, Integer idCompra);

  List<CompraDTO> listarDetalleCompraAdmin(Integer idCompra);

  ResultadoSP anularCompra(Integer idUsuario, Integer idCompra);

  ResultadoSP revertirCompra(Integer idUsuario, Integer idCompra);

  ResultadoSP filtrarMisComprasRangoFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  ResultadoSP cerrarCompra(Integer idCompra);

  ResultadoSP deshacerCerrarCompra (Integer idCompra);

  ResultadoSP filtrarComprasUsuarioFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  // Listar todas las compras
  long contarTotalCompras();

  List<CompraDTO> listarTotalCompras(int pagina, int limite);

  // Listar compras confirmadas
  long contarComprasConfirmadas();

  List<CompraDTO> listarComprasConfirmadas(int pagina, int limite);

  // Listar compras pendientes
  List<CompraDTO> listarComprasPendientes();

  // Listar compras anuladas
  long contarComprasAnuladas();

  List<CompraDTO> listarComprasAnuladas(int pagina, int limite);

  CompraDTO obtenerCompraPorId(Integer idCompra);
}
