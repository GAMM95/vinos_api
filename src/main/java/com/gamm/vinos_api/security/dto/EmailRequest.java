package com.gamm.vinos_api.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    String email
) {
}
