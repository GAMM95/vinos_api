package com.gamm.vinos_api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.exception.security.InvalidRefreshTokenException;
import com.gamm.vinos_api.exception.security.TokenExpiradoException;
import com.gamm.vinos_api.exception.security.TokenInvalidoException;
import com.gamm.vinos_api.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService customUserDetailsService;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    try {
      if (header != null && header.startsWith("Bearer ")) {
        String token = header.substring(7);
        String username = jwtUtil.obtenerUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails =
              customUserDetailsService.loadUserByUsername(username);

          UsernamePasswordAuthenticationToken auth =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities()
              );

          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      }

      filterChain.doFilter(request, response);

    } catch (TokenExpiradoException ex) {
      escribirError(response, HttpServletResponse.SC_UNAUTHORIZED, "El token ha expirado, vuelva a iniciar sesión.");
    } catch (TokenInvalidoException ex){
      escribirError(response,HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o corrupto.");
    } catch (InvalidRefreshTokenException ex){
      escribirError(response, HttpServletResponse.SC_UNAUTHORIZED, "Refresh token inválido.");
    }

  }

  // Usar sincronizando ResponseVO
  private void escribirError(HttpServletResponse response, int status, String mensaje) throws IOException {
    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    objectMapper.writeValue(response.getOutputStream(), ResponseVO.error(mensaje));
  }
}

