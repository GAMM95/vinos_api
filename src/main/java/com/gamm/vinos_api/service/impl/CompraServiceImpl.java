package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.CarritoCompraDTO;
import com.gamm.vinos_api.dto.view.CompraDTO;
import com.gamm.vinos_api.dto.view.ProductosCarritoDTO;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.CompraRepository;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl extends BaseService implements CompraService {

  private final CompraRepository compraRepository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  // ─── Carrito ──────────────────────────────────────────────────────────────

  @Override
  public ResultadoSP agregarProductoCarrito(Compra compra) {
    return compraRepository.agregarProductoCarrito(
        getIdUsuarioAutenticado(),
        compra,
        compra.getDetalles().getFirst()
    );
  }

  @Override
  public ResultadoSP eliminarProductoCarrito(Integer idDetalleCompra) {
    ResultadoSP resultado =  compraRepository.eliminarProductoCarrito(getIdUsuarioAutenticado(), idDetalleCompra);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP actualizarCantidadProductoCarrito(Compra compra) {
    ResultadoSP resultado =  compraRepository.actualizarCantidadProductoCarrito(
        getIdUsuarioAutenticado(),
        compra,
        compra.getDetalles().getFirst()
    );
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public long contarProductosCarritoUsuario() {
    Integer idUsuario = getIdUsuarioAutenticado();
    Compra carrito = compraRepository.obtenerCarritoPendiente(idUsuario);
    return carrito != null
        ? compraRepository.contarProductosCarrito(idUsuario, carrito.getIdCompra())
        : 0L;
  }

  @Override
  public List<ProductosCarritoDTO> listarProductosCarritoUsuario() {
    Integer idUsuario = getIdUsuarioAutenticado();
    Compra carrito = compraRepository.obtenerCarritoPendiente(idUsuario);
    return carrito != null
        ? compraRepository.listarProductosCarritos(idUsuario, carrito.getIdCompra())
        : List.of();
  }

  @Override
  public List<CarritoCompraDTO> listarCarritosCompra() {
    return compraRepository.listarCarritosCompra();
  }

  // ─── Operaciones de compra ────────────────────────────────────────────────

  @Override
  public ResultadoSP confirmarCompra(Compra compra) {
    Integer idUsuario = getIdUsuarioAutenticado();
    if (compra == null) compra = new Compra();

    ResultadoSP resultado = compraRepository.confirmarCompra(idUsuario, compra);
    ResponseVO.validar(resultado);

    CompraDTO c = compraRepository.obtenerCompraPorId(compra.getIdCompra());
    webSocketService.notifyDashboardUpdate();
    webSocketService.notifyCompraUpdate();

    if (esAdministrador()) {
      notificacionService.notificarRol(
          "Vendedor", "INFO", "Compra registrada por el administrador",
          "El administrador ha registrado la compra " + c.getCodCompra() + " en el sistema.",
          "/mercaderia/consultar-mercaderia"
      );
    } else {
      notificacionService.notificarRol(
          "Administrador", "SUCCESS", "Nueva compra registrada",
          c.getUsuario() + " ha registrado la compra " + c.getCodCompra() + " en el sistema.",
          "/compras/consultar-compra"
      );
    }
    return resultado;
  }

  @Override
  public ResultadoSP cerrarCompra(Integer idCompra) {
    ResultadoSP resultado = compraRepository.cerrarCompra(idCompra);
    ResponseVO.validar(resultado);

    CompraDTO compra = compraRepository.obtenerCompraPorId(idCompra);
    webSocketService.notifyDashboardUpdate();
    webSocketService.notifyCompraUpdate();

    if (esAdministrador()) {
      notificacionService.notificarRol(
          "Vendedor", "INFO", "Nueva mercadería disponible",
          "Se ha recepcionado nueva mercadería en el almacén. Compra " + compra.getCodCompra() + " cerrada.",
          "/mercaderia/consultar-mercaderia"
      );
    } else {
      notificacionService.notificarUsuario(
          compra.getIdUsuario(), compra.getUsername(),
          "SUCCESS", "Tu compra fue cerrada",
          "Tu compra " + compra.getCodCompra() + " ha sido recepcionada en el almacén.",
          "/mercaderia/mi-stock"
      );
    }
    return resultado;
  }

  @Override
  public ResultadoSP deshacerCerrarCompra(Integer idCompra) {
    ResultadoSP resultado = compraRepository.deshacerCerrarCompra(idCompra);
    ResponseVO.validar(resultado);

    CompraDTO compra = compraRepository.obtenerCompraPorId(idCompra);
    webSocketService.notifyDashboardUpdate();
    webSocketService.notifyCompraUpdate();

    notificacionService.notificarRol(
        "Vendedor", "WARNING", "Reversión de cierre de compra",
        "Se ha revertido el cierre de la compra " + compra.getCodCompra() + ". Puede haber cambios en el stock.",
        "/mercaderia/consultar-mercaderia"
    );

    if (compra.getIdUsuario() != null) {
      notificacionService.notificarUsuario(
          compra.getIdUsuario(), compra.getUsuario(),
          "WARNING", "Tu compra fue revertida",
          "El administrador ha revertido el cierre de tu compra " + compra.getCodCompra() + ". Verifique el stock.",
          "/mercaderia/mi-stock"
      );
    }
    return resultado;
  }

  @Override
  public ResultadoSP anularCompra(Integer idCompra) {
    ResultadoSP resultado = compraRepository.anularCompra(getIdUsuarioAutenticado(), idCompra);
    ResponseVO.validar(resultado);

    CompraDTO compra = compraRepository.obtenerCompraPorId(idCompra);
    webSocketService.notifyDashboardUpdate();
    webSocketService.notifyCompraUpdate();

    notificacionService.notificarRol(
        "Administrador", "WARNING", "Compra anulada",
        compra.getUsuario() + " ha anulado la compra " + compra.getCodCompra() + ".",
        "/compras/consultar-compra"
    );
    return resultado;
  }

  @Override
  public ResultadoSP revertirCompra(Integer idCompra) {
    ResultadoSP resultado = compraRepository.revertirCompra(getIdUsuarioAutenticado(), idCompra);
    ResponseVO.validar(resultado);

    CompraDTO compra = compraRepository.obtenerCompraPorId(idCompra);
    webSocketService.notifyDashboardUpdate();
    webSocketService.notifyCompraUpdate();

    notificacionService.notificarRol(
        "Administrador", "INFO", "Reversión de compra",
        compra.getUsuario() + " ha revertido la compra " + compra.getCodCompra() + ".",
        "/compras/consultar-compra"
    );
    return resultado;
  }

  // ─── Consultas del usuario autenticado ────────────────────────────────────

  @Override
  public PaginaResultado<CompraDTO> listarComprasUsuario(int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    List<CompraDTO> data = compraRepository.listarComprasUsuario(idUsuario, pagina, limite);
    long total = compraRepository.contarComprasUsuario(idUsuario);
    return construirPagina(data, pagina, limite, total);
  }


  @Override
  public List<CompraDTO> listarDetalleCompraUsuario(Integer idCompra) {
    return compraRepository.listarDetalleComprasUsuario(getIdUsuarioAutenticado(), idCompra);
  }

  @Override
  public PaginaResultado<CompraDTO> filtrarMisComprasPorFechas(
      LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite
  ) {
    Integer idUsuario = getIdUsuarioAutenticado();
    ResultadoSP resultado = compraRepository.filtrarMisComprasRangoFechas(idUsuario, fechaInicio, fechaFin);
    ResponseVO.validar(resultado);

    @SuppressWarnings("unchecked")
    List<CompraDTO> todos = resultado.getData() != null
        ? (List<CompraDTO>) resultado.getData()
        : List.of();

    // Paginación en memoria — el SP retorna todos los registros filtrados
    List<CompraDTO> contenido = paginarEnMemoria(todos, pagina, limite);
    long total = todos.size();
    return construirPagina(contenido, pagina, limite, total);
  }

  // ─── Administración ───────────────────────────────────────────────────────

  @Override
  public List<CompraDTO> listarDetalleCompraAdmin(Integer idCompra) {
    return compraRepository.listarDetalleCompraAdmin(idCompra);
  }

  @Override
  public List<CompraDTO> listarComprasPendientes() {
    return compraRepository.listarComprasPendientes();
  }

  @Override
  public PaginaResultado<CompraDTO> listarTotalCompras(int pagina, int limite) {
    List<CompraDTO> data = compraRepository.listarTotalCompras(pagina, limite);
    long total = compraRepository.contarTotalCompras();
    return construirPagina(data, pagina, limite, total);
  }

  @Override
  public PaginaResultado<CompraDTO> listarComprasConfirmadas(int pagina, int limite) {
    List<CompraDTO> data = compraRepository.listarComprasConfirmadas(pagina, limite);
    long total = compraRepository.contarComprasConfirmadas();
    return construirPagina(data, pagina, limite, total);
  }

  @Override
  public PaginaResultado<CompraDTO> listarComprasAnuladas(int pagina, int limite) {
    List<CompraDTO> contenido = compraRepository.listarComprasAnuladas(pagina, limite);
    long total = compraRepository.contarComprasAnuladas();
    return construirPagina(contenido, pagina, limite, total);
  }

  @Override
  public PaginaResultado<CompraDTO> filtrarComprasPorUsuarioYFechas(
      Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin,
      int pagina, int limite
  ) {
    ResultadoSP resultado = compraRepository.filtrarComprasUsuarioFechas(
        idUsuario, fechaInicio, fechaFin
    );
    ResponseVO.validar(resultado);

    @SuppressWarnings("unchecked")
    List<CompraDTO> todos = resultado.getData() != null
        ? (List<CompraDTO>) resultado.getData()
        : List.of();

    List<CompraDTO> data = paginarEnMemoria(todos, pagina, limite);
    long total = todos.size();

    return construirPagina(data, pagina, limite, total);
  }

  // ✅ Paginación en memoria para SPs que retornan todos los registros de una vez
  private <T> List<T> paginarEnMemoria(List<T> lista, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    if (offset >= lista.size()) return List.of();
    return lista.subList(offset, Math.min(offset + limite, lista.size()));
  }
}
