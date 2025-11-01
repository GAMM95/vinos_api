package com.gamm.vinos_api.security.dto;

public record AuthResponse(
    String token,
    String mensaje
) {
}