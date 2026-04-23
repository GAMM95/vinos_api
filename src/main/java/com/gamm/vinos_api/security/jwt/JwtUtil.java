package com.gamm.vinos_api.security.jwt;

import com.gamm.vinos_api.exception.security.InvalidRefreshTokenException;
import com.gamm.vinos_api.exception.security.TokenExpiradoException;
import com.gamm.vinos_api.exception.security.TokenInvalidoException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final Key key;
  private final long expiracion;
  private final long refreshExpiracion;

  public JwtUtil(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") long expiracion,
      @Value("${jwt.refresh-expiration}") long refreshExpiration
  ) {
    if (secret == null || secret.length() < 32)
      throw new IllegalArgumentException("jwt.secret mínimo 32 chars para HS256");

    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.expiracion = expiracion;
    this.refreshExpiracion = refreshExpiration;
  }

  public String generarToken(String username) {
    return buildToken(username, expiracion, "access");
  }

  public String generarRefreshToken(String username) {
    return buildToken(username, refreshExpiracion, "refresh");
  }

  // Metodo privado reutilizable
  private String buildToken(String username, long expiracion, String type) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiracion))
        .claim("type", type)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  // Obtener Claims y lanzar excepciones personalizadas
  private Claims extraerClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException ex) {
      throw new TokenExpiradoException("El token ha expirado", ex);
    } catch (MalformedJwtException | SecurityException | IllegalArgumentException ex) {
      throw new TokenInvalidoException("Token inválido", ex);
    } catch (Exception ex) {
      throw new TokenInvalidoException("Error al procesar el token", ex);
    }
  }

  // Obtener username del token
  public String obtenerUsername(String token) {
    return extraerClaims(token).getSubject();
  }

  // Validar tipo refresh
  public void validarRefreshToken(String token) {
    Claims claims = extraerClaims(token);
    String type = claims.get("type", String.class);

    if (!"refresh".equals(type)) {
      throw new InvalidRefreshTokenException("Este no es un refresh token válido");
    }
  }
}