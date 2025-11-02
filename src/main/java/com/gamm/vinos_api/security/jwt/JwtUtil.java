package com.gamm.vinos_api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final Key key;
  private final long expiracion;

  public JwtUtil(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") long expiracion
  ) {
    if (secret == null || secret.length() < 32)
      throw new IllegalArgumentException("jwt.secret mínimo 32 chars para HS256");

    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.expiracion = expiracion;
  }

  // Generar token
  public String generarToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiracion))
        .claim("type", "access")
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  // Generar Refresh Token
  public String generarRefreshToken(String username) {
    long refreshExpiration = 7 * 24 * 60 * 60 * 1000; // 7 días
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
        .claim("type", "refresh")
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  // Obtener username del token
  public String obtenerUsername(String token) {
    return extraerClaims(token).getSubject();
  }

  private Claims extraerClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
