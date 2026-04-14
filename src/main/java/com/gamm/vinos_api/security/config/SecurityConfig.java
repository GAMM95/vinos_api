package com.gamm.vinos_api.security.config;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.gamm.vinos_api.security.jwt.JwtAuthenticationEntryPoint;
import com.gamm.vinos_api.security.service.CustomUserDetailsService;
import com.gamm.vinos_api.security.jwt.JwtAuthFilter;
import com.gamm.vinos_api.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationEntryPoint jwt;
  private final JwtUtil jwtUtil;
  private final ObjectMapper objectMapper;

  @Bean
  public JwtAuthFilter jwtAuthFilter(CustomUserDetailsService customUserDetailsService) {
    return new JwtAuthFilter(jwtUtil, customUserDetailsService, objectMapper );
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter, CorsConfigurationSource corsConfigurationSource, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // WebSocket
            .requestMatchers("/ws/**").permitAll()
            // 🔓 Swagger (IMPORTANTE)
            .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
            ).permitAll()
            // 🔓 públicos
            .requestMatchers("/api/v1/auth/**").permitAll()
            .requestMatchers("/recuperar-password").permitAll()
            .requestMatchers("/FotosUsuarios/**").permitAll()
            .requestMatchers(HttpMethod.POST,"/api/v1/usuarios/username/verificacion").permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 🔐 protegidos
            .requestMatchers(HttpMethod.POST, "/api/v1/usuarios/*/foto").authenticated()
            .requestMatchers("/api/**").authenticated()
            // 🔐 todo lo demás
            .anyRequest().authenticated()
        )
        .exceptionHandling(ex ->ex
            // 401 - no autenticado (request llega sin token o con token invalido)
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            // 403 - autenticado pero sin permisos suficientes
            .accessDeniedHandler((request, response, accessDeniedException) -> {
              response.setStatus(HttpStatus.FORBIDDEN.value());
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);
              response.setCharacterEncoding("UTF-8");
              objectMapper.writeValue(response.getWriter(), accessDeniedException);
            })

        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
