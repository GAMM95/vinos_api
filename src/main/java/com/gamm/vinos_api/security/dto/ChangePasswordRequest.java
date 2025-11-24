package com.gamm.vinos_api.security.dto;

public record ChangePasswordRequest(
    Integer IdUsuario,
    String actual,
    String nueva
) {
}
