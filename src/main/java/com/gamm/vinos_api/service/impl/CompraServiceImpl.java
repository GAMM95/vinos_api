package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.dto.view.CarritoCompraView;
import com.gamm.vinos_api.dto.view.CompraView;
import com.gamm.vinos_api.dto.view.ProductosCarritoView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.CompraRepository;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

  @Autowired
  private CompraRepository compraRepository;

  /* Helpers */
  private Integer getUsuario() {
    Integer idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) throw new IllegalStateException("Usuario no logueado");
    return idUsuario;
  }

  private ResponseVO listarDetalleCompra(Integer idCompra, Integer idUsuario) {
    List<CompraView> detalles =
        idUsuario != null
            ? compraRepository.listarDetalleComprasUsuario(idUsuario, idCompra)
            : compraRepository.listarDetalleCompraAdmin(idCompra);

    return ResponseVO.success(detalles);
  }

  private ResponseVO paginar(List<CompraView> data, int pagina, int limite, long totalRegistros) {
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);
    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResultadoSP agregarProductoCarrito(Compra compra) {
    Integer idUsuario = getUsuario();
    return compraRepository.agregarProductoCarrito(
        idUsuario,
        compra,
        compra.getDetalles().get(0)
    );
  }

  @Override
  public ResultadoSP eliminarProductoCarrito(Integer idDetalleCompra) {
    Integer idUsuario = getUsuario();
    return compraRepository.eliminarProductoCarrito(idUsuario, idDetalleCompra);
  }

  @Override
  public ResultadoSP actualizarCantidadProductoCarrito(Compra compra) {
    Integer idUsuario = getUsuario();
    return compraRepository.actualizarCantidadProductoCarrito(
        idUsuario,
        compra,
        compra.getDetalles().get(0)
    );
  }

  @Override
  public ResultadoSP confirmarCompra(Compra compra) {
    Integer idUsuario = getUsuario();

    if (compra == null) {
      compra = new Compra();
    }

    return compraRepository.confirmarCompra(idUsuario, compra);
  }

  @Override
  public ResultadoSP cerrarCompra(Integer idCompra) {
    return compraRepository.cerrarCompra(idCompra);
  }

  @Override
  public ResultadoSP deshacerCerrarCompra(Integer idCompra) {
    return compraRepository.deshacerCerrarCompra(idCompra);
  }

  @Override
  public long contarProductosCarritoUsuario() {
    Integer idUsuario = getUsuario();
    Compra carrito = compraRepository.obtenerCarritoPendiente(idUsuario);
    return carrito != null
        ? compraRepository.contarProductosCarrito(idUsuario, carrito.getIdCompra())
        : 0L;
  }

  @Override
  public List<ProductosCarritoView> listarProductosCarritoUsuario() {
    Integer idUsuario = getUsuario();
    Compra carrito = compraRepository.obtenerCarritoPendiente(idUsuario);
    return carrito != null
        ? compraRepository.listarProductosCarritos(idUsuario, carrito.getIdCompra())
        : List.of();
  }

  @Override
  public ResultadoSP anularCompra(Integer idCompra) {
    Integer idUsuario = getUsuario();
    return compraRepository.anularCompra(idUsuario, idCompra);
  }

  @Override
  public ResultadoSP revertirCompra(Integer idCompra) {
    Integer idUsuario = getUsuario();
    return compraRepository.revertirCompra(idUsuario, idCompra);
  }

  @Override
  public ResponseVO filtrarMisComprasPorFechas(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Integer idUsuario = getUsuario();
    ResultadoSP resultado = compraRepository.filtrarMisComprasRangoFechas(idUsuario, fechaInicio, fechaFin);

    if (!resultado.esExitoso()) {
      return ResponseVO.error(resultado.getMensaje());
    }

    @SuppressWarnings("unchecked")
    List<CompraView> data = resultado.getData() != null
        ? (List<CompraView>) resultado.getData()
        : List.of();

    return paginar(
        data,
        pagina,
        limite,
        data.size()
    );
  }

  @Override
  public ResponseVO filtrarComprasPorUsuarioYFechas(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {

    ResultadoSP resultado = compraRepository.filtrarComprasUsuarioFechas(idUsuario, fechaInicio, fechaFin);

    if (!resultado.esExitoso()) {
      return ResponseVO.error(resultado.getMensaje());
    }

    @SuppressWarnings("unchecked")
    List<CompraView> data = resultado.getData() != null
        ? (List<CompraView>) resultado.getData()
        : List.of();

    return paginar(data, pagina, limite, data.size());
  }

  @Override
  public List<CarritoCompraView> listarCarritosCompra() {
    return compraRepository.listarCarritosCompra();
  }

  @Override
  public ResponseVO listarComprasUsuario(int pagina, int limite) {
    Integer idUsuario = getUsuario();
    // Obtener la lista de compras de cada usuario
    List<CompraView> comprasPagina = compraRepository.listarComprasUsuario(idUsuario, pagina, limite);

    // Obtener el total de registros para calcular las paginas
    long totalRegistros = compraRepository.contarComprasUsuario(idUsuario);
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);

    // Retornar usando ResponseVO con paginación
    return ResponseVO.paginated(
        comprasPagina,
        pagina,
        limite,
        totalPaginas,
        totalRegistros
    );
  }

  @Override
  public ResponseVO listarDetalleCompraUsuario(Integer idCompra) {
    return listarDetalleCompra(idCompra, getUsuario());
  }

  @Override
  public ResponseVO listarDetalleCompraAdmin(Integer idCompra) {
    return listarDetalleCompra(idCompra, null);
  }

  @Override
  public ResponseVO listarTotalCompras(int pagina, int limite) {
    //  Obtener la lista de la pagina
    List<CompraView> comprasPagina = compraRepository.listarTotalCompras(pagina, limite);

    // Obtener el total de registros para calcular páginas
    long totalRegistros = compraRepository.contarTotalCompras();
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);

    // Retornar usando ResponseVO con paginación
    return ResponseVO.paginated(
        comprasPagina,
        pagina,
        limite,
        totalPaginas,
        totalRegistros
    );
  }

  @Override
  public ResponseVO listarComprasConfirmadas(int pagina, int limite) {
    return paginar(
        compraRepository.listarComprasConfirmadas(pagina, limite),
        pagina,
        limite,
        compraRepository.contarComprasConfirmadas()
    );
  }

  @Override
  public List<CompraView> listarComprasPendientes() {
    return compraRepository.listarComprasPendientes();
  }

  @Override
  public ResponseVO listarComprasAnuladas(int pagina, int limite) {
    List<CompraView> comprasPagina = compraRepository.listarComprasAnuladas(pagina, limite);

    long totalRegistros = compraRepository.contarComprasAnuladas();
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);
    return ResponseVO.paginated(
        comprasPagina,
        pagina,
        limite,
        totalPaginas,
        totalRegistros
    );
  }

}
