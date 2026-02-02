package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.model.DetalleCompra;
import com.gamm.vinos_api.domain.view.ProductosCarritoView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface CompraRepository {

  // Operaciones que modifican el carrito
  ResultadoSP agregarProductoCarrito(Integer idUsuario, Compra compra, DetalleCompra detalle);
  ResultadoSP eliminarProductoCarrito(Integer idUsuario, Integer idDetCompra);
  ResultadoSP actualizarCantidadProductoCarrito(Integer idUsuario, Compra compra, DetalleCompra detalle);
  ResultadoSP crearCompra(Integer idUsuario, Compra compra);

  // Operaciones que consultan el carrito
  long contarProductosCarrito(Integer idUsuario, Integer idCompra);
  List<ProductosCarritoView> listarProductosCarritos(Integer idUsuario, Integer idCompra);

  // Obtener carrito pendiente del usuario
  Compra obtenerCarritoPendiente(Integer idUsuario);
}
