package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.dto.view.DistribucionView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.DistribucionRepository;
import com.gamm.vinos_api.service.DistribucionService;
import com.gamm.vinos_api.util.ResultadoSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DistribucionServiceImpl implements DistribucionService {

  @Autowired
  private DistribucionRepository repository;

  @Override
  public ResultadoSP distribuirProducto(DistribucionSucursal distribucionSucursal) {
    return repository.distribuirProducto(distribucionSucursal);
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
