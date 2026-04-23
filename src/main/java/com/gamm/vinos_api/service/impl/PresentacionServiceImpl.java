package com.gamm.vinos_api.service.impl;


import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.PresentacionDTO;
import com.gamm.vinos_api.repository.PresentacionRepository;
import com.gamm.vinos_api.service.PresentacionService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PresentacionServiceImpl implements PresentacionService {

  private final PresentacionRepository presentacionRepository;

  @Override
  public ResultadoSP guardarPresentacion(Presentacion presentacion) {
    ResultadoSP resultado = presentacionRepository.guardarPresentacion(presentacion);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP actualizarPresentacion(Presentacion presentacion) {
    ResultadoSP resultado = presentacionRepository.actualizarPresentacion(presentacion);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP darBaja(Integer idPresentacion) {
    ResultadoSP resultado = presentacionRepository.darBaja(idPresentacion);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP darAlta(Integer idPresentacion) {
    ResultadoSP resultado = presentacionRepository.darAlta(idPresentacion);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP filtrarPresentacion(String descripcion) {
    ResultadoSP resultado = presentacionRepository.filtrarPresentacion(descripcion);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public List<PresentacionDTO> listarPresentaciones() {
    return presentacionRepository.listarPresentaciones();
  }
}
