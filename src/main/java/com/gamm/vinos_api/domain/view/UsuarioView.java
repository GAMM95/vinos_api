package com.gamm.vinos_api.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioView {
  private Integer idUsuario;
  private String username;
  private String nombreCompleto;
  private String celular;
  private String rutaFoto;
  private String estado;
}
