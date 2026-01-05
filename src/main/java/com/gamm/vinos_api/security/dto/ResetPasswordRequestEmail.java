package com.gamm.vinos_api.security.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequestEmail(
    @NotBlank(message = "Token es obligatorio")
    String token,

    @NotBlank(message = "La nueva contraseña es obligatoria")
    String nuevaPassword
) {}
