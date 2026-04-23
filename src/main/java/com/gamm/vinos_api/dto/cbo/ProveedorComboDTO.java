package com.gamm.vinos_api.dto.cbo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorComboDTO {
  private Integer idProveedor;
  private String razonSocial;
}
