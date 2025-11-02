package com.gamm.vinos_api.security.dto;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    String mensaje
) {
}
