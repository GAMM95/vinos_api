package com.gamm.vinos_api.security.dto;

public record LoginRequest(
    String username,
    String password
) {}