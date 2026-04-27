package com.gamm.vinos_api.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "empresa")
public class EmpresaProperties {
  private String nombre;
  private String ruc;
  private String telefono;
  private String direccion;
}
