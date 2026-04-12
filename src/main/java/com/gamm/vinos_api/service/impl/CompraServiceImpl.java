package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.dto.view.CarritoCompraView;
import com.gamm.vinos_api.dto.view.CompraView;
import com.gamm.vinos_api.dto.view.ProductosCarritoView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.CompraRepository;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

  private final CompraRepository compraRepository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  /* Helpers */
  private Integer getUsuario() {
    Integer idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) throw new IllegalStateException("Usuario no logueado");
    return idUsuario;
  }

  private String getRolActual() {
    String rol = SecurityUtils.getRol();
    if (rol == null) throw new IllegalArgumentException("Rol no disponible");
    return rol;
  }

  private String getNombreCompletoAutenticado() {
    String nombreCompleto = SecurityUtils.getNombreCompleto();
    if (nombreCompleto == null) {
      throw new IllegalStateException("El usuario no logueado");
    }
    return nombreCompleto;
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
    String rol = getRolActual();

    if (compra == null) {
      compra = new Compra();
    }
    ResultadoSP resultado = compraRepository.confirmarCompra(idUsuario, compra);

    if (!resultado.esExitoso()) {
      return resultado;
    }
    CompraView c = compraRepository.obtenerCompraPorId(compra.getIdCompra());

    // Actualizaciones del sistema
    webSocketService.notifyDashboardUpdate();
    webSocketService.notifyCompraUpdate();
    // notificar al administrador que el vendedor ha realizado una nueva compra
    if ("VENDEDOR".equalsIgnoreCase(rol)) {
      notificacionService.notificarRol(
          "Administrador",
          "SUCCESS",
          "Nueva compra registrada",
          c.getUsuario() + " ha registrado la compra " + c.getCodCompra() + " en el sistema.",
          "/compras/consultar-compra"
      );
    }
      // Notificar a todos los vendedores que se ha realizado una compra
    else if ("ADMINISTRADOR".equalsIgnoreCase(rol)) {
      notificacionService.notificarRol(
          "Vendedor",
          "INFO",
          "Compra registrada por el administrador",
          "El administrador ha registrado la compra " + c.getCodCompra() + " en el sistema.",
          "/mercaderia/consultar-mercaderia"
      );
    }
    return resultado;
  }

  @Override
  public ResultadoSP cerrarCompra(Integer idCompra) {
    ResultadoSP resultado = compraRepository.cerrarCompra(idCompra);
    CompraView compra = compraRepository.obtenerCompraPorId(idCompra);

    if (resultado.esExitoso()) {
      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyCompraUpdate();

      String rol = compra.getRol();
      // Notificar a los vendedores en general
      if ("ADMINISTRADOR".equalsIgnoreCase(rol)) {
        // La compra es del admin → notificar a todos los vendedores
        notificacionService.notificarRol(
            "Vendedor",
            "INFO",
            "Nueva mercadería disponible",
            "Se ha recepcionado nueva mercadería en el almacén. Compra " + compra.getCodCompra() + " cerrada.",
            "/mercaderia/consultar-mercaderia"
        );
      } else {
        // La compra es de un vendedor → notificar solo a ese vendedor
        notificacionService.notificarUsuario(
            compra.getIdUsuario(),
            compra.getUsername(),
            "SUCCESS",
            "Tu compra fue cerrada",
            "Tu compra " + compra.getCodCompra() + " ha sido recepcionada en el almacén.",
            "/mercaderia/mi-stock"
        );
      }
    }
    return resultado;
  }

  @Override
  public ResultadoSP deshacerCerrarCompra(Integer idCompra) {
    ResultadoSP resultado = compraRepository.deshacerCerrarCompra(idCompra);
    CompraView compra = compraRepository.obtenerCompraPorId(idCompra);
    if (resultado.esExitoso()) {
      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyCompraUpdate();
      // Notificar a todos los vendedores (stock puede cambiar)
      notificacionService.notificarRol(
          "Vendedor",
          "WARNING",
          "Reversión de cierre de compra",
          "Se ha revertido el cierre de la compra " + compra.getCodCompra() + ". Puede haber cambios en el stock.",
          "/mercaderia/consultar-mercaderia"
      );
      // Notificar específicamente al vendedor dueño de la compra
      if (compra.getIdUsuario() != null) {
        notificacionService.notificarUsuario(
            compra.getIdUsuario(),
            compra.getUsuario(),
            "WARNING",
            "Tu compra fue revertida",
            "El administrador ha revertido el cierre de tu compra " + compra.getCodCompra() + ". Verifique el stock.",
            "/mercaderia/mi-stock"
        );
      }
    }
    return resultado;
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
    ResultadoSP resultado = compraRepository.anularCompra(idUsuario, idCompra);
    CompraView compra = compraRepository.obtenerCompraPorId(idCompra);
    if (resultado.esExitoso()) {
      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyCompraUpdate();
      // Notificar al administrador que el usuario ha anulado compra
      notificacionService.notificarRol(
          "Administrador",
          "WARNING",
          "Compra anulada",
          compra.getUsuario() + " ha anulado la compra " + compra.getCodCompra() + ".",
          "/compras/consultar-compra"
      );
    }
    return resultado;
  }

  @Override
  public ResultadoSP revertirCompra(Integer idCompra) {
    Integer idUsuario = getUsuario();
    ResultadoSP resultado = compraRepository.revertirCompra(idUsuario, idCompra);
    CompraView compra = compraRepository.obtenerCompraPorId(idCompra);

    if (resultado.esExitoso()) {
      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyCompraUpdate();
      // Notificar al administrador que el usuario a revertido compra
      notificacionService.notificarRol(
          "Administrador",
          "INFO",
          "Reversión de compra",
          compra.getUsuario() + " ha revertido la compra " + compra.getCodCompra() + ".",
          "/compras/consultar-compra"
      );
    }
    return resultado;
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
