package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface PresentacionService {
  ResultadoSP guardarPresentacion(Presentacion presentacion);

  ResultadoSP actualizarPresentacion(Presentacion presentacion);

  ResultadoSP darBaja(Integer idPresentacion);

  ResultadoSP darAlta(Integer idPresentacion);

  List<Presentacion> listarPresentaciones();
}
