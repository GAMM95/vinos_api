package com.gamm.vinos_api.security.config;

import com.gamm.vinos_api.exception.security.TokenExpiradoException;
import com.gamm.vinos_api.exception.security.TokenInvalidoException;
import com.gamm.vinos_api.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

  private final JwtUtil jwtUtil;
  private final ApplicationContext applicationContext; // en lugar de CustomUserDetailsService

  // Para ese momento WebSocketConfig ya está completamente inicializado
  private UserDetailsService getUserDetailsService() {
    return applicationContext.getBean(UserDetailsService.class);
  }

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor =
        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    if (accessor == null || !StompCommand.CONNECT.equals(accessor.getCommand())) {
      return message;
    }

    String authHeader = accessor.getFirstNativeHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      log.warn("WebSocket CONNECT sin token — conexión anónima");
      return message;
    }

    String token = authHeader.substring(7);

    try {
      String username = jwtUtil.obtenerUsername(token);
      UserDetails userDetails = getUserDetailsService().loadUserByUsername(username);

      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities()
          );

      accessor.setUser(auth);
      log.info("WebSocket autenticado para username: {}", username);

    } catch (TokenExpiradoException ex) {
      log.warn("WebSocket CONNECT rechazado — token expirado");
    } catch (TokenInvalidoException ex) {
      log.warn("WebSocket CONNECT rechazado — token inválido");
    } catch (Exception ex) {
      log.error("WebSocket CONNECT — error inesperado: {}", ex.getMessage());
    }

    return message;
  }
}