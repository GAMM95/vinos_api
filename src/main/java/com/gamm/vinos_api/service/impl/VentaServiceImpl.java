package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.DetalleVenta;
import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.CarritoVentaDTO;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.DetalleVentaDTO;
import com.gamm.vinos_api.dto.view.VentaDTO;
import com.gamm.vinos_api.repository.VentaRepository;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.service.VentaService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaServiceImpl extends BaseService implements VentaService {

  // Inyección por constructor
  private final VentaRepository ventaRepository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  // OPERACIONES DE CARRITO
  @Override
  public List<CarritoVentaDTO> listarCarritoVentaUsuario() {
    return ventaRepository.listarCarritoVentaUsuario(getIdUsuarioAutenticado());
  }

  @Override
  public List<CarritoVentaDTO> listarCarritoVentaAdmin(Integer idVenta) {
    return ventaRepository.listarCarritoVentaAdmin(idVenta);
  }

  @Override
  public ResultadoSP agregarCarritoVenta(Venta venta) {
    Integer idUsuario = getIdUsuarioAutenticado();

    log.debug("Agregando producto al carrito idUsuario={}", idUsuario);

    DetalleVenta detalle = venta.getDetalles().stream()
        .findFirst()
        .orElseThrow(() -> {
          log.warn("Intento de agregar carrito sin producto idUsuario={}", idUsuario);
          return new IllegalArgumentException("Debe enviar un producto.");
        });

    ResultadoSP resultado = ventaRepository.agregarProductoCarrito(idUsuario, venta, detalle);

    if (!resultado.esExitoso())
      log.warn("Error agregando producto al carrito idUsuario={}, mensaje={}", idUsuario, resultado.getMensaje());

    return resultado;
  }

  @Override
  public ResultadoSP retirarProductoCarrito(Integer idVenta, Integer idVino) {
    Integer idUsuario = getIdUsuarioAutenticado();

    log.debug("Retirando producto del carrito idUsuario={}, idVenta={}, idVino={}", idUsuario, idVenta, idVino);

    ResultadoSP resultado = ventaRepository.retirarProductoCarrito(idUsuario, idVenta, idVino);

    if (!resultado.esExitoso())
      log.warn("Error retirando producto carrito idVenta={}, mensaje={}", idVenta, resultado.getMensaje());

    return resultado;
  }

  // OPERACIONES PRINCIPALES — notifican al vendedor + admins + dashboard
  @Override
  public ResultadoSP confirmarVenta(Integer idVenta, String metodoPago, BigDecimal descuento) {
    Integer idUsuario = getIdUsuarioAutenticado();

    log.info("Confirmando venta idVenta={}, idUsuario={}, metodoPago={}, descuento={}",
        idVenta, idUsuario, metodoPago, descuento);

    ResultadoSP resultado = ventaRepository.confirmarVenta(idUsuario, idVenta, metodoPago, descuento);

    if (resultado.esExitoso()) {
      log.info("Venta confirmada correctamente idVenta={}, idUsuario={}", idVenta, idUsuario);
      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyVentaUpdate();

      // Obtener los datos de la venta
      VentaDTO venta = ventaRepository.obtenerVentaPorId(idVenta);

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

    log.info("Intentando anular venta idVenta={}, rol={}", idVenta, rolNormalizado);

    // 1. Obtener venta ANTES de anular
    VentaDTO venta;
    try {
      venta = ventaRepository.obtenerVentaPorId(idVenta);
      if (venta == null) {
        log.warn("Venta no encontrada antes de anular idVenta={}", idVenta);
        return ventaRepository.cancelarVenta(idVenta);
      }
    } catch (Exception e) {
      log.error("Error obteniendo venta antes de anular idVenta={}", idVenta, e);
      return ventaRepository.cancelarVenta(idVenta);
    }

    // 2. Anular
    ResultadoSP resultado = ventaRepository.cancelarVenta(idVenta);

    if (resultado.esExitoso()) {
      log.info("Venta anulada correctamente idVenta={}, rol={}", idVenta, rolNormalizado);

      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyVentaUpdate();

      if ("Vendedor".equals(rolNormalizado)) {
        log.warn("Venta anulada por vendedor idVenta={}, usuario={}", idVenta, venta.getUsuario());

        notificacionService.notificarRol(
            "Administrador", "WARNING",
            "Venta anulada por vendedor",
            "El vendedor " + venta.getUsuario() + " anuló su propia venta " + venta.getCodVenta(),
            "/ventas/consultar-ventas"
        );

      } else if ("Administrador".equals(rolNormalizado)) {
        log.warn("Venta anulada por administrador idVenta={}, usuarioAfectado={}", idVenta, venta.getUsername());

        notificacionService.notificarUsuario(
            venta.getIdUsuario(), venta.getUsername(),
            "WARNING", "Tu venta fue anulada",
            "El administrador anuló tu venta " + venta.getCodVenta() + ". Contacta a soporte si crees que es un error.",
            "/ventas/mis-ventas"
        );
      }
    } else {
      log.warn("Error al anular venta idVenta={}, mensaje={}", idVenta, resultado.getMensaje());
    }
    return resultado;
  }

  // CONSULTAS — sin notificaciones (son lecturas, no acciones de estado)
  @Override
  public PaginaResultado<VentaDTO> listarVentasUsuario(int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();
    List<VentaDTO> data = ventaRepository.listarVentasUsuario(idUsuario, pagina, limite);
    long totalRegistros = ventaRepository.contarVentasUsuario(idUsuario);
    return construirPagina(data, pagina, limite, totalRegistros);
  }

  @Override
  public PaginaResultado<VentaDTO> listarTotalVenta(int pagina, int limite) {
    List<VentaDTO> ventasPagina = ventaRepository.listarTotalVentas(pagina, limite);
    long totalRegistros = ventaRepository.contarTotalVentas();
    return construirPagina(ventasPagina, pagina, limite, totalRegistros);
  }

  @Override
  public List<DetalleVentaDTO> listarDetalleVenta(Integer idVenta) {
    return ventaRepository.listarDetalleVenta(idVenta);
  }

  @Override
  public PaginaResultado<VentaDTO> filtrarMisVentasPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Integer idUsuario = getIdUsuarioAutenticado();

    log.debug("Filtrando ventas idUsuario={}, desde={}, hasta={}", idUsuario, fechaInicio, fechaFin);

    ResultadoSP resultadoSP = ventaRepository.filtrarMisVentasPorFechas(idUsuario, fechaInicio, fechaFin, pagina, limite);
    ResponseVO.validar(resultadoSP);

    @SuppressWarnings("unchecked")
    List<VentaDTO> data = (List<VentaDTO>) resultadoSP.getData();
    long totalRegistros = ventaRepository.contarVentasPorFechas(idUsuario, fechaInicio, fechaFin);

    return construirPagina(data, pagina, limite, totalRegistros);
  }

  @Override
  public PaginaResultado<VentaDTO> filtrarVentasPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    ResultadoSP resultadoSP = ventaRepository.filtrarVentasPorUsuarioOFechas(
        idUsuario, fechaInicio, fechaFin, pagina, limite
    );

    ResponseVO.validar(resultadoSP);

    @SuppressWarnings("unchecked")
    List<VentaDTO> data = (List<VentaDTO>) resultadoSP.getData();
    long totalRegistros = ventaRepository.contarVentasPorUsuarioOFechas(idUsuario, fechaInicio, fechaFin);

    return construirPagina(data, pagina, limite, totalRegistros);
  }
}
