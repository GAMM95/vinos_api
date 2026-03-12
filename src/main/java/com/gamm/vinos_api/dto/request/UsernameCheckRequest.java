package com.gamm.vinos_api.dto.request;

public record UsernameCheckRequest(
    Integer idUsuario,
    String username
) {
}