package com.gamm.vinos_api.security.dto;

public record RegisterRequest(
    String nombres,
    String apellidoPaterno,
    String apellidoMaterno,
    String username,
    String password
) {}