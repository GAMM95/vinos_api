package com.gamm.vinos_api.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateRequest {
  private String nombres;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String celular;
  private String domicilio;
  private Integer idUsuario;
  private String username;
  private String rutaFoto;
  private MultipartFile foto; // <- aquí la imagen

  // getters y setters
}
