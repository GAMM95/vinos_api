package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorView {
 private Integer idProveedor;
 private String codProveedor;
 private String razonSocial;
 private String ruc;
 private String contacto;
 private String origen;
 private String ubicacion;
 private String estado;
}