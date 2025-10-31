package com.gamm.vinos_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
  private String username;
  private String password;
  private String nombres;
  private String apellidoPaterno;
  private String apellidoMaterno;
}
