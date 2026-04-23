package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.view.DistribucionDTO;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.repository.DistribucionRepository;
import com.gamm.vinos_api.service.DistribucionService;
import com.gamm.vinos_api.service.NotificacionService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistribucionServiceImpl extends BaseService implements DistribucionService {

  private final DistribucionRepository repository;
  private final WebSocketService webSocketService;
  private final NotificacionService notificacionService;

  @Override
  public ResultadoSP distribuirProducto(DistribucionSucursal distribucionSucursal) {
    ResultadoSP resultado = repository.distribuirProducto(distribucionSucursal);
    ResponseVO.validar(resultado);

      webSocketService.notifyDashboardUpdate();
      webSocketService.notifyMercaderiaUpdate();
      List<DistribucionDTO> data = repository.listarRepartosSucursal(1, 1);

      if (data == null|| data.isEmpty()) return resultado;
      DistribucionDTO d = data.getFirst();

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

    return resultado;
  }

  @Override
  public PaginaResultado<DistribucionDTO> filtrarRepartosPorSucursalORango(Integer idSucursal, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    ResultadoSP resultadoSP = repository.filtrarRepartoSucursalRango(idSucursal, fechaInicio, fechaFin, pagina, limite);

    ResponseVO.validar(resultadoSP);

    @SuppressWarnings("unchecked")
    List<DistribucionDTO> data = resultadoSP.getData() != null
        ? (List<DistribucionDTO>) resultadoSP.getData()
        : List.of();
    long totalRegistros = repository.contarRepartosSucursalRango(  idSucursal, fechaInicio, fechaFin );

    return construirPagina(data, pagina, limite, totalRegistros);
  }

  @Override
  public PaginaResultado<DistribucionDTO> listarRepartoSucursal(int pagina, int limite) {
    List<DistribucionDTO> data = repository.listarRepartosSucursal(pagina, limite);
    long totalRegistros = repository.contarRepartos();
    return construirPagina(data, pagina, limite, totalRegistros);
  }

}
