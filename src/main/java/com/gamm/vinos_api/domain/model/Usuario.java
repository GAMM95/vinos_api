package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import com.gamm.vinos_api.domain.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

  private Integer idUsuario;
  private Persona persona;
  private String username;
  private String password;
  private Rol rol = Rol.VENDEDOR;
  private EstadoRegistro estado = EstadoRegistro.INACTIVO;
}
