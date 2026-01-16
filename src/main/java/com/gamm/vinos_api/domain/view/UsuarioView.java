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
  private Integer idPersona;
  private String nombres;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String nombreCompleto;
  private String celular;
  private String email;
  private String domicilio;
  private Integer idSucursal;
  private String nombreSucursal;
  private String rutaFoto;
  private String rol;
  private String estado;
}
