package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.util.ResultadoSP;

public interface PasswordResetTokenRepository {
  ResultadoSP invalidateByUser(Integer idUsuario);

  ResultadoSP save(Integer idUsuario, String tokenHash);

  ResultadoSP validate(String tokenHash);

  ResultadoSP markAsUsed(String tokenHash);
}
