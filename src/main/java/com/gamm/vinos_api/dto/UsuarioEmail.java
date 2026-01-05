package com.gamm.vinos_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEmail {
  Integer idUsuario;
  String email;
  String nombreCompleto;
}
