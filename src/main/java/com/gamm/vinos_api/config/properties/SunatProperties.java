package com.gamm.vinos_api.config.properties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "sunat")
public class SunatProperties {
  private boolean activo = false;   // switch principal
  private String ruc;
  private String usuario;
  private String password;
  private String certificado;
  private String claveCertificado;
  private Url url = new Url();

  @Getter
  @Setter
  public static class Url {
    private String boleta;
    private String factura;
  }
}