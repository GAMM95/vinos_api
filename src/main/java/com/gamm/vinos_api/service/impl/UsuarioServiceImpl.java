package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.config.WebSocketService;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.UsuarioDTO;
import com.gamm.vinos_api.exception.business.BusinessException;
import com.gamm.vinos_api.repository.UsuarioAuthRepository;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.service.FotoService;
import com.gamm.vinos_api.service.UsuarioService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl extends BaseService implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioAuthRepository usuarioAuthRepository;
  private final PasswordEncoder passwordEncoder;
  private final FotoService fotoService;
  private final WebSocketService webSocketService;

  // ─── Autenticación ────────────────────────────────────────────────────────

  @Override
  public ResultadoSP login(String username) {
    // NO llama ResponseVO.validar() — CustomUserDetailsService maneja el fallo
    // con UsernameNotFoundException, no con BusinessException
    return usuarioAuthRepository.login(username);
  }

  @Override
  public ResultadoSP obtenerPerfil() {
    // NO llama ResponseVO.validar() — AuthService lo valida con ResponseVO.validar()
    return usuarioAuthRepository.obtenerDatosPerfil(SecurityUtils.getUsername());
  }

  // ─── Registro y contraseñas ───────────────────────────────────────────────

  @Override
  public ResultadoSP registrarUsuario(Usuario usuario) {
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    //  Retorna ResultadoSP sin validar — el controller llama ResponseVO.validar()
    return usuarioAuthRepository.registrarUsuario(usuario);
  }

  @Override
  public ResultadoSP resetearPassword(Integer idUsuario, String nuevaPassword) {
    String encriptada = passwordEncoder.encode(nuevaPassword);
    return usuarioAuthRepository.resetearPassword(idUsuario, encriptada);
  }

  @Override
  public ResultadoSP resetearPasswordToken(Integer idUsuario, String nuevaPassword) {
    // Contraseña ya viene hasheada desde AuthService
    return usuarioAuthRepository.resetearPassword(idUsuario, nuevaPassword);
  }

  @Override
  public void cambiarPassword(String actual, String nueva) {
    Integer idUsuario = getIdUsuarioAutenticado();

    Usuario u = usuarioRepository.obtenerUsuarioConPassword(idUsuario);
    if (u == null) {
      log.warn("Usuario no encontrado al cambiar contraseña. idUsuario={}", idUsuario);
      throw new BusinessException("Usuario no encontrado."); // excepción
    }
    if (!passwordEncoder.matches(actual, u.getPassword())) {
      log.warn("Contraseña actual incorrecta. idUsuario={}", idUsuario);
      throw new BusinessException("La contraseña actual es incorrecta."); // excepción
    }

    String nuevaEncriptada = passwordEncoder.encode(nueva);
    ResultadoSP resultado = usuarioAuthRepository.cambiarPassword(
        idUsuario, u.getPassword(), nuevaEncriptada
    );
    ResponseVO.validar(resultado); // válido aquí — no es autenticación
    log.info("Contraseña cambiada para idUsuario: {}", idUsuario);
  }

  // ─── CRUD ─────────────────────────────────────────────────────────────────

  @Override
  public void inactivarUsuario(Integer idUsuario) {
    ResultadoSP resultado = usuarioRepository.inactivarUsuario(idUsuario);
    ResponseVO.validar(resultado); // lanza BusinessException con mensaje del SP
    log.info("Usuario inactivado: {}", idUsuario);
  }

  @Override
  public void activarUsuario(Integer idUsuario, Integer idSucursal) {
    ResultadoSP resultado = usuarioRepository.activarUsuario(idUsuario, idSucursal);
    ResponseVO.validar(resultado);
    log.info("Usuario activado: {} en sucursal {}", idUsuario, idSucursal);
  }

  @Override
  public Usuario actualizarUsuario(Usuario usuario) {
    Integer idUsuario = usuario.getIdUsuario();
    log.info("Actualizando usuario idUsuario={}", idUsuario);

    ResultadoSP resultado = usuarioRepository.actualizarUsuario(usuario);
    ResponseVO.validar(resultado); // lanza si SP falla

    Usuario actualizado = usuarioRepository.obtenerPorId(idUsuario);
    webSocketService.notifyUsuarioUpdate();

    log.info("Usuario actualizado correctamente idUsuario={}", idUsuario);

    return actualizado; // retorna el dato, no ResultadoSP
  }

  @Override
  public ResultadoSP actualizarFoto(Integer idUsuario, MultipartFile foto) {
    log.info("Actualizando foto para idUsuario={}", idUsuario);

    //  Separado en dos try-catch con responsabilidades distintas
    String ruta;
    try {
      ruta = fotoService.guardarFoto(idUsuario, foto);
    } catch (Exception e) {
      log.error("Error al guardar foto. idUsuario={}, archivo={}, size={}", idUsuario, foto.getOriginalFilename(), foto.getSize(), e);
      return new ResultadoSP(0, "Error al procesar la foto.");
      //  Este catch es solo para errores de I/O — no captura BusinessException
    }

    // Si llega aquí, la foto se guardó en disco correctamente
    ResultadoSP resultado = usuarioRepository.actualizarFoto(idUsuario, ruta);
    ResponseVO.validar(resultado); //  fuera del try — BusinessException se propaga normalmente

    webSocketService.notifyUsuarioUpdate();

    log.info("Foto actualizada correctamente idUsuario={}, ruta={}", idUsuario, ruta);

    resultado.setData(ruta);
    return resultado;
  }

  @Override
  public void verificarUsername(String username, Integer idUsuario) {
    ResultadoSP resultado = usuarioRepository.verificarUsername(username, idUsuario);
    ResponseVO.validar(resultado); // lanza BusinessException si username no disponible
    log.debug("Username disponible='{}'", username);
  }

  // ─── Consultas ────────────────────────────────────────────────────────────

  @Override
  public List<UsuarioDTO> listarUsuarios() {
    return usuarioRepository.listarUsuarios();
  }

  @Override
  public PaginaResultado<UsuarioDTO> listarUsuariosPaginados(int pagina, int limite) {
    List<UsuarioDTO> data = usuarioRepository.listarUsuariosPaginados(pagina, limite);
    Long total = usuarioRepository.contarUsuarios();

    log.debug("Usuarios obtenidos={}, total={}", data.size(), total);

    return construirPagina(data, pagina, limite, total);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<UsuarioDTO> filtrarUsuario(String terminoBusqueda) {
    ResultadoSP resultado = usuarioRepository.filtrarUsuario(terminoBusqueda);
    ResponseVO.validar(resultado);
    return resultado.getData() != null
        ? (List<UsuarioDTO>) resultado.getData()
        : List.of();
  }

  @Override
  public Usuario obtenerPorId(Integer idUsuario) {
    return usuarioRepository.obtenerPorId(idUsuario);
  }

  @Override
  public Usuario obtenerUsuarioPorEmail(String email) {
    return usuarioRepository.obtenerUsuarioPorEmail(email);
  }

}