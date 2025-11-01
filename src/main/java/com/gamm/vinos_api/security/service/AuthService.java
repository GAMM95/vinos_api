package com.gamm.vinos_api.security.service;


import com.gamm.vinos_api.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager am;
  private final JwtUtil jwtUtil;

  public String login(String username, String password) {
    try {
      am.authenticate(
          new UsernamePasswordAuthenticationToken(username, password)
      );
    } catch (AuthenticationException ex) {
      throw new BadCredentialsException("Usuario o contraseña incorrectos s");
    }

    // Genera token con username
    return jwtUtil.generarToken(username);
  }
}
