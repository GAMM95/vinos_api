package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.entities.dto.views.VinoView;
import com.gamm.vinos_api.entities.model.Vino;
import com.gamm.vinos_api.repository.VinoRepository;
import com.gamm.vinos_api.service.VinoService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VinoServiceImpl implements VinoService {

  private final VinoRepository vinoRepository;

  @Override
  public ResultadoSP registrarVino(Vino vino) {
    return vinoRepository.registrarVino(vino);
  }

  @Override
  public ResultadoSP actualizarVino(Vino vino) {
    return vinoRepository.actualizarVino(vino);
  }

  @Override
  public ResultadoSP eliminarVinoPorId(Integer idVino) {
    return vinoRepository.eliminarVinoPorId(idVino);
  }

  @Override
  public ResultadoSP filtrarVinoPorNombre(String nombre) {
    return vinoRepository.filtrarVinoPorNombre(nombre);
  }

  @Override
  public List<VinoView> listarVinos() {
    return vinoRepository.listarVinos();
  }
}
