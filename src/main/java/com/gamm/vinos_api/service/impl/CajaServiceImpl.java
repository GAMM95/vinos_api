package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.domain.model.Sucursal;
import com.gamm.vinos_api.domain.model.Usuario;
import com.gamm.vinos_api.domain.view.CajaView;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.repository.CajaRepository;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.CajaService;
import com.gamm.vinos_api.utils.ResultadoSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CajaServiceImpl implements CajaService {

  @Autowired
  private CajaRepository cajaRepository;

  /* Helpers */
  private Integer getUsuarioAutenticado() {
    Integer idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) {
      throw new IllegalStateException("Usuario no logueado");
    }
    return idUsuario;
  }

  private Integer getSucursalAutenticada() {
    Integer idSucursal = SecurityUtils.getSucursalId();
    if (idSucursal == null) {
      throw new IllegalStateException("El usuario no tiene sucursal asignada");
    }
    return idSucursal;
  }

  @Override
  public ResultadoSP abrirCaja(Caja caja) {
    Integer idUsuario = getUsuarioAutenticado();

    Usuario usuario = new Usuario();
    usuario.setIdUsuario(idUsuario);
    caja.setUsuario(usuario);

    // Resolver sucursal según rol
    String rol = SecurityUtils.getRol();

    if ("ADMINISTRADOR".equalsIgnoreCase(rol)) {

      // Admin debe elegir sucursal
      if (caja.getSucursal() == null || caja.getSucursal().getIdSucursal() == null) {
        return new ResultadoSP(0, "Debe seleccionar una sucursal");
      }

    } else {
      Integer idSucursal = getSucursalAutenticada();

      if (idSucursal <= 0) {
        return new ResultadoSP(0, "No tienes una sucursal asignada.\nContacta al administrador.");
      }

      Sucursal sucursal = new Sucursal();
      sucursal.setIdSucursal(idSucursal);
      caja.setSucursal(sucursal);
    }
    return cajaRepository.abrirCaja(caja);
  }

  @Override
  public ResultadoSP cerrarCaja(Integer idCaja) {
    return cajaRepository.cerrarCaja(idCaja);
  }

  @Override
  public ResponseVO listarMisCajas(int pagina, int limite) {
    Integer idUsuario = getUsuarioAutenticado();
    List<CajaView> data = cajaRepository.listarMisCajas(idUsuario, pagina, limite);

    long totalRegistros = cajaRepository.contarMisCajas(idUsuario);
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO listarTotalCajas(int pagina, int limite) {

    long totalRegistros = cajaRepository.contarTotalCajas();
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);
    List<CajaView> data = cajaRepository.listarTotalCajas(pagina, limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }

  @Override
  public ResponseVO filtrarMisCajasPorRango(LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    Integer idUsuario = getUsuarioAutenticado();

    ResultadoSP resultadoSP = cajaRepository.filtrarMisCajasPorRango(idUsuario, fechaInicio, fechaFin, pagina, limite);

    @SuppressWarnings("unchecked")
    List<CajaView> data = (List<CajaView>) resultadoSP.getData();

    long totalRegistros = cajaRepository.contarMisCajasPorRango(idUsuario, fechaInicio, fechaFin);
    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);

  }

  @Override
  public ResponseVO filtrarCajasPorUsuarioORango(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina, int limite) {
    ResultadoSP resultadoSP = cajaRepository.filtrarTotalCajasPorRango(idUsuario, fechaInicio, fechaFin, pagina, limite);

    @SuppressWarnings("unchecked")
    List<CajaView> data = (List<CajaView>) resultadoSP.getData();

    long totalRegistros;
    if (idUsuario != null) {
      totalRegistros = cajaRepository.contarTotalCajasPorRango(idUsuario, fechaInicio, fechaFin);
    } else {
      totalRegistros = cajaRepository.contarTotalCajasPorRango(null, fechaInicio, fechaFin);
    }

    int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

    return ResponseVO.paginated(data, pagina, limite, totalPaginas, totalRegistros);
  }
}
