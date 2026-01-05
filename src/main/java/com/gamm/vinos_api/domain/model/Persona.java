package com.gamm.vinos_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
  private Integer idPersona;
  private String nombres;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String celular;
  private String email;
  private String domicilio;
}