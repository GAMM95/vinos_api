package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Usuario;
import org.springframework.web.multipart.MultipartFile;

public interface FotoService {
  String guardarFoto( Integer IdUsuario, MultipartFile foto) throws Exception;
}
