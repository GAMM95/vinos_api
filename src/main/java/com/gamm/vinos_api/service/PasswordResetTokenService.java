package com.gamm.vinos_api.service;

import com.gamm.vinos_api.utils.ResultadoSP;

public interface PasswordResetTokenService {
  ResultadoSP invalidarPorUsuario(Integer idUsuario);

  ResultadoSP crearToken(Integer idUsuario, String tokenPlano);

  ResultadoSP validarToken(String tokenPlano);

  ResultadoSP marcarComoUsado(String tokenPlano);
}
