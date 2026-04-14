package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.dto.view.DistribucionView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.DistribucionRepository;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.DistribucionService;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistribucionServiceImpl implements DistribucionService {

  private final DistribucionRepository repository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  @Override
  public ResultadoSP distribuirProducto(DistribucionSucursal distribucionSucursal) {
    ResultadoSP resultado = repository.distribuirProducto(distribucionSucursal);

    if (resultado.esExitoso()) {
      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyMercaderiaUpdate();
      List<DistribucionView> data = repository.listarRepartosSucursal(1, 1);

      DistribucionView d = data.get(0);

      String mensaje = "Se ha distribuido " +
          d.getCantidad() + " unidades de vino " +
          d.getVino() + " (" + d.getPresentacion() + ") a la sucursal " +
          d.getSucursal() + ".";

      notificacionService.notificarRol(
          "Vendedor",
          "INFO",
          "Nueva distribución de vino",
          mensaje,
          "/mercaderia/mi-stock"
      );
    }
    return resultado;
  }

  @Override
  public ResponseVO filtrarRepartosPorSucursalORango(Integer idSucursal, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    ResultadoSP resultadoSP = repository.filtrarRepartoSucursalRango(idSucursal, fechaInicio, fechaFin, pagina, limite);

    if (!resultadoSP.esExitoso()) {
      return ResponseVO.error(resultadoSP.getMensaje());
    }

    @SuppressWarnings("unchecked")
    List<DistribucionView> data = (List<DistribucionView>) resultadoSP.getData();

    long totalRegistros;
    if (idSucursal != null) {
      totalRegistros = repository.contarRepartosSucursalRango(idSucursal, fechaInicio, fechaFin);
    } else {
      totalRegistros = repository.contarRepartosSucursalRango(null, fechaInicio, fechaFin);
    }
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);
    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO listarRepartoSucursal(int pagina, int limite) {
    List<DistribucionView> data = repository.listarRepartosSucursal(pagina, limite);

    long totalRegistros = repository.contarRepartos();
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) pagina);
    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

}
