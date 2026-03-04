package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.DistribucionSucursal;
import com.gamm.vinos_api.domain.view.CajaView;
import com.gamm.vinos_api.domain.view.DistribucionView;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.repository.DistribucionRepository;
import com.gamm.vinos_api.service.DistribucionService;
import com.gamm.vinos_api.utils.ResultadoSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public ResponseVO listarRepartoSucursal(int pagina, int limite) {
    List<DistribucionView> data = repository.listarRepartosSucursal(pagina, limite);

    long totalRegistros = repository.contarRepartos();
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) pagina);
    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

}
