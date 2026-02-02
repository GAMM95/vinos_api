package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.view.ProductosCarritoView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface CompraService {

  // Operaciones que modifican el carrito
  ResultadoSP agregarProductoCarrito(Integer idUsuario, Compra compra);
  ResultadoSP eliminarProductoCarrito(Integer idUsuario, Integer idDetalleCompra);
  ResultadoSP actualizarCantidadProductoCarrito(Integer idUsuario, Compra compra);
  ResultadoSP crearCompra(Integer idUsuario, Compra compra);

  // Operaciones de consulta
  long contarProductosCarrito(Integer idUsuario, Integer idCompra);
  List<ProductosCarritoView> listarProductosCarrito(Integer idUsuario, Integer idCompra);

}
