package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.view.ProductosCarritoView;
import com.gamm.vinos_api.repository.CompraRepository;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

  @Autowired
  private CompraRepository compraRepository;

  private Integer getUsuario() {
    Integer idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) throw new IllegalStateException("Usuario no logueado");
    return idUsuario;
  }

  // ===================== Métodos públicos =====================
  @Override
  public ResultadoSP agregarProductoCarrito(Integer idUsuario, Compra compra) {
    if (idUsuario == null) idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) return new ResultadoSP(0, "Usuario no logueado", null);

    return compraRepository.agregarProductoCarrito(idUsuario, compra, compra.getDetalles().get(0));
  }

  @Override
  public ResultadoSP eliminarProductoCarrito(Integer idUsuario, Integer idDetalleCompra) {
    if (idUsuario == null) idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) return new ResultadoSP(0, "Usuario no logueado", null);

    return compraRepository.eliminarProductoCarrito(idUsuario, idDetalleCompra);
  }

  @Override
  public ResultadoSP actualizarCantidadProductoCarrito(Integer idUsuario, Compra compra) {
    if (idUsuario == null) idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) return new ResultadoSP(0, "Usuario no logueado", null);

    return compraRepository.actualizarCantidadProductoCarrito(idUsuario, compra, compra.getDetalles().get(0));
  }

  @Override
  public ResultadoSP crearCompra(Integer idUsuario, Compra compra) {
    if (idUsuario == null) idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) return new ResultadoSP(0, "Usuario no logueado", null);

    return compraRepository.crearCompra(idUsuario, compra);
  }

  @Override
  public long contarProductosCarrito(Integer idUsuario, Integer idCompra) {
    idUsuario = idUsuario != null ? idUsuario : getUsuario();
    Compra carrito = compraRepository.obtenerCarritoPendiente(idUsuario);
    return carrito != null ? compraRepository.contarProductosCarrito(idUsuario, carrito.getIdCompra()) : 0L;
  }

  @Override
  public List<ProductosCarritoView> listarProductosCarrito(Integer idUsuario, Integer idCompra) {
    idUsuario = idUsuario != null ? idUsuario : getUsuario();
    Compra carrito = compraRepository.obtenerCarritoPendiente(idUsuario);
    return carrito != null
        ? compraRepository.listarProductosCarritos(idUsuario, carrito.getIdCompra())
        : List.of();
  }
}
