package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.domain.view.PresentacionView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface PresentacionRepository {
  ResultadoSP guardarPresentacion(Presentacion presentacion);

  ResultadoSP actualizarPresentacion(Presentacion presentacion);

  ResultadoSP darBaja(Integer idPresentacion);

  ResultadoSP darAlta(Integer idPresentacion);

  ResultadoSP filtrarPresentacion(String descripcion);

  List<PresentacionView> listarPresentaciones();
}
