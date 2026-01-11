package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.repository.PasswordResetTokenRepository;
import com.gamm.vinos_api.service.PasswordResetTokenService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

  private final PasswordResetTokenRepository tokenRepository;

  @Override
  public ResultadoSP invalidarPorUsuario(Integer idUsuario) {
    return tokenRepository.invalidateByUser(idUsuario);
  }

  @Override
  public ResultadoSP crearToken(Integer idUsuario, String tokenPlano) {
    String tokenHash = DigestUtils.sha256Hex(tokenPlano);
    return tokenRepository.save(idUsuario, tokenHash);
  }

  @Override
  public ResultadoSP validarToken(String tokenPlano) {
    String tokenHash = DigestUtils.sha256Hex(tokenPlano);
    return tokenRepository.validate(tokenHash);
  }

  @Override
  public ResultadoSP marcarComoUsado(String tokenPlano) {
    String tokenHash = DigestUtils.sha256Hex(tokenPlano);
    return tokenRepository.markAsUsed(tokenHash);
  }
}
