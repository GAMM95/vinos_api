package com.gamm.vinos_api.security.dto;

public record ResetPasswordRequest(
    Integer IdUsuario,
    String nuevaPassword
    ) {
}
