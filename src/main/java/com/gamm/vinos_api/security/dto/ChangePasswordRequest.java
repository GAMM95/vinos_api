package com.gamm.vinos_api.security.dto;

public record ChangePasswordRequest(
    String actual,
    String nueva
) {
}
