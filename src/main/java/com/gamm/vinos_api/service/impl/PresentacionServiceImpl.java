package com.gamm.vinos_api.service.impl;


import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.repository.PresentacionRepository;
import com.gamm.vinos_api.service.PresentacionService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PresentacionServiceImpl implements PresentacionService {

  private final PresentacionRepository presentacionRepository;

  @Override
  public ResultadoSP guardarPresentacion(Presentacion presentacion) {
    return presentacionRepository.guardarPresentacion(presentacion);
  }

  @Override
  public ResultadoSP actualizarPresentacion(Presentacion presentacion) {
    return presentacionRepository.actualizarPresentacion(presentacion);
  }

  @Override
  public ResultadoSP darBaja(Integer idPresentacion) {
    return presentacionRepository.darBaja(idPresentacion);
  }

  @Override
  public ResultadoSP darAlta(Integer idPresentacion) {
    return presentacionRepository.darAlta(idPresentacion);
  }

  @Override
  public List<Presentacion> listarPresentaciones() {
    return presentacionRepository.listarPresentaciones();
  }
}
