package com.gamm.vinos_api.service;


import com.gamm.vinos_api.entities.dto.views.VinoView;
import com.gamm.vinos_api.entities.model.Vino;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface VinoService {
  ResultadoSP registrarVino(Vino vino);

  ResultadoSP actualizarVino(Vino vino);

  ResultadoSP eliminarVinoPorId(Integer idVino);

  ResultadoSP filtrarVinoPorNombre(String nombre);

  List<VinoView> listarVinos();
}
