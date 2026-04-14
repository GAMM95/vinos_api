package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.view.CarritoVentaView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.VentaView;
import com.gamm.vinos_api.repository.VentaRepository;
import com.gamm.vinos_api.service.BaseService;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.service.VentaService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl extends BaseService implements VentaService {

  // Inyección por constructor
  private final VentaRepository ventaRepository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  // OPERACIONES DE CARRITO
  @Override
  public List<CarritoVentaView> listarCarritoVentaUsuario() {
    return ventaRepository.listarCarritoVentaUsuario(getIdUsuarioAutenticado());
  }

  @Override
  public List<CarritoVentaView> listarCarritoVentaAdmin(Integer idVenta) {
    return ventaRepository.listarCarritoVentaAdmin(idVenta);
  }

  @Override
  public ResultadoSP agregarCarritoVenta(Venta venta) {
    Integer idUsuario = getIdUsuarioAutenticado();
    DetalleVenta detalle = venta.getDetalles().stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Debe enviar un producto."));
    return ventaRepository.agregarProductoCarrito(idUsuario, venta, detalle);
  }

  @Override
  public ResultadoSP retirarProductoCarrito(Integer idVenta, Integer idVino) {
    Integer idUsuario = getIdUsuarioAutenticado();
    return ventaRepository.retirarProductoCarrito(idUsuario, idVenta, idVino);
  }

  // OPERACIONES PRINCIPALES — notifican al vendedor + admins + dashboard
  @Override
  public ResultadoSP confirmarVenta(Integer idVenta, String metodoPago, BigDecimal descuento) {
    Integer idUsuario = getIdUsuarioAutenticado();
    ResultadoSP resultado = ventaRepository.confirmarVenta(idUsuario, idVenta, metodoPago, descuento);

    if (resultado.esExitoso()) {
      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyVentaUpdate();

      // Obtener los datos de la venta
      VentaView venta = ventaRepository.obtenerVentaPorId(idVenta);

      notificacionService.notificarRol(
          "Administrador",
          "SUCCESS",
          "Nueva venta registrada",
          venta.getUsuario() + " generó la venta " + venta.getCodVenta() + " por S/. " + venta.getTotal() + ".",
          "/ventas/consultar-ventas"
      );
    }
    return resultado;
  }

  @Override
  public ResultadoSP anularVenta(Integer idVenta) {
    String rolActual = getRolAutenticado();
    String rolNormalizado = rolActual.substring(0, 1).toUpperCase() + rolActual.substring(1).toLowerCase();

    // 1. Obtener venta ANTES de anular
    VentaView venta;
    try {
      venta = ventaRepository.obtenerVentaPorId(idVenta);
      if (venta == null) {
        return ventaRepository.cancelarVenta(idVenta);
      }
    } catch (Exception e) {
      return ventaRepository.cancelarVenta(idVenta);
    }

    // 2. Anular
    ResultadoSP resultado = ventaRepository.cancelarVenta(idVenta);

    if (resultado.esExitoso()) {
      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyVentaUpdate();
      if ("Vendedor".equals(rolNormalizado)) {
        notificacionService.notificarRol(
            "Administrador", "WARNING",
            "Venta anulada por vendedor",
            "El vendedor " + venta.getUsuario() + " anuló su propia venta " + venta.getCodVenta(),
            "/ventas/consultar-ventas"
        );

      } else if ("Administrador".equals(rolNormalizado)) {
        notificacionService.notificarUsuario(
            venta.getIdUsuario(), venta.getUsername(),
            "WARNING", "Tu venta fue anulada",
            "El administrador anuló tu venta " + venta.getCodVenta() + ". Contacta a soporte si crees que es un error.",
            "/ventas/mis-ventas"
        );
      }
    }
    return resultado;
  }

  // CONSULTAS — sin notificaciones (son lecturas, no acciones de estado)
  @Override
  public ResponseVO listarVentasUsuario(int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    List<VentaView> ventasPagina = ventaRepository.listarVentasUsuario(idUsuario, pagina, limite);
    long totalRegistros = ventaRepository.contarVentasUsuario(idUsuario);
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);
    return ResponseVO.paginated(ventasPagina, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO listarTotalVenta(int pagina, int limite) {
    List<VentaView> ventasPagina = ventaRepository.listarTotalVentas(pagina, limite);
    long totalRegistros = ventaRepository.contarTotalVentas();
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);
    return ResponseVO.paginated(ventasPagina, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO listarDetalleVenta(Integer idVenta) {
    return ResponseVO.success(ventaRepository.listarDetalleVenta(idVenta));
  }

  @Override
  public ResponseVO filtrarMisVentasPorRango(LocalDate fechaInicio, LocalDate fechaFin,  int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    ResultadoSP resultadoSP = ventaRepository.filtrarMisVentasPorFechas(
        idUsuario, fechaInicio, fechaFin, pagina, limite
    );

    if (!resultadoSP.esExitoso()) return ResponseVO.error(resultadoSP.getMensaje());

    @SuppressWarnings("unchecked")
    List<VentaView> data = (List<VentaView>) resultadoSP.getData();
    long totalRegistros = ventaRepository.contarVentasPorFechas(idUsuario, fechaInicio, fechaFin);
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO filtrarVentasPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    ResultadoSP resultadoSP = ventaRepository.filtrarVentasPorUsuarioOFechas(
        idUsuario, fechaInicio, fechaFin, pagina, limite
    );

    if (!resultadoSP.esExitoso()) return ResponseVO.error(resultadoSP.getMensaje());

    @SuppressWarnings("unchecked")
    List<VentaView> data = (List<VentaView>) resultadoSP.getData();
    long totalRegistros = ventaRepository.contarVentasPorUsuarioOFechas(idUsuario, fechaInicio, fechaFin);
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }
}
