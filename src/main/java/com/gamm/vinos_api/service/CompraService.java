package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.view.CarritoCompraView;
import com.gamm.vinos_api.domain.view.CompraView;
import com.gamm.vinos_api.domain.view.ProductosCarritoView;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.time.LocalDate;
import java.util.List;

public interface CompraService {

  // Operaciones que modifican el carrito
  ResultadoSP agregarProductoCarrito(Compra compra);

  ResultadoSP eliminarProductoCarrito(Integer idDetalleCompra);

  ResultadoSP actualizarCantidadProductoCarrito(Compra compra);

  ResultadoSP confirmarCompra(Compra compra);

  // Operaciones de consulta
  long contarProductosCarritoUsuario();

  List<ProductosCarritoView> listarProductosCarritoUsuario();

  ResultadoSP anularCompra(Integer idCompra);

  ResultadoSP revertirCompra(Integer idCompra);

  ResponseVO filtrarMisComprasPorFechas(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite);

  ResponseVO filtrarComprasPorUsuarioYFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);

  // Listados
  List<CarritoCompraView> listarCarritosCompra();

  // Listar compras de cada Usuario
  ResponseVO listarComprasUsuario(int pagina, int limite);

  ResponseVO listarDetalleCompraUsuario(Integer idCompra);

  ResponseVO listarDetalleCompraAdmin(Integer idCompra);

  // Listar todas las compras
  ResponseVO listarTotalCompras(int pagina, int limite);

  // Listar compras confirmadas
  ResponseVO listarComprasConfirmadas(int pagina, int limite);

  // Listar compras pendientes
  List<CompraView> listarComprasPendientes();

  // Listar compras anuladas
  ResponseVO listarComprasAnuladas(int pagina, int limite);
}
