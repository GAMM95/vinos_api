package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.service.FotoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FotoServiceImpl implements FotoService {

  private static final String RUTA_BASE = "D:/FotosUsuarios/";
  private static final String RUTA_PUBLICA = "/FotosUsuarios/";

  private final UsuarioRepository usuarioRepository;

  public FotoServiceImpl(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public String guardarFoto(Integer idUsuario, MultipartFile foto) throws Exception {
    Usuario usuario = usuarioRepository.obtenerPorId(idUsuario);
    if (usuario == null) {
      throw new IllegalArgumentException("Usuario no encontrado");
    }

    // Validar tipo MIME
    String contentType = foto.getContentType();
    if (contentType == null ||
        !(contentType.equalsIgnoreCase("image/jpeg") || contentType.equalsIgnoreCase("image/png"))) {
      throw new IllegalArgumentException("Solo se permiten imágenes en formato JPG o PNG");
    }

    // Validar imagen
    BufferedImage image = ImageIO.read(foto.getInputStream());
    if (image == null) {
      throw new IllegalArgumentException("El archivo no es una imagen válida");
    }

    // Crear carpeta si no existe
    Path carpeta = Paths.get(RUTA_BASE);
    Files.createDirectories(carpeta);

    // Determinar nombre de archivo (mismo nombre cada vez)
    String extension = contentType.equalsIgnoreCase("image/png") ? ".png" : ".jpg";
    String nombreArchivo = "usuario_" + idUsuario + extension;
    Path destino = carpeta.resolve(nombreArchivo);

    // Eliminar foto anterior si existe
    if (Files.exists(destino)) {
      Files.delete(destino);
    }

    // Guardar nueva foto
    Files.copy(foto.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

    // Retornar ruta pública
    return RUTA_PUBLICA + nombreArchivo;
  }

}
