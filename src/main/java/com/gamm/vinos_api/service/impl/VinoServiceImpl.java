package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.queries.VinosCompraDTO;
import com.gamm.vinos_api.dto.view.VinoDTO;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.VinoRepository;
import com.gamm.vinos_api.service.VinoService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VinoServiceImpl implements VinoService {

  private final VinoRepository vinoRepository;

  @Override
  public ResultadoSP registrarVino(Vino vino) {
    ResultadoSP resultado = vinoRepository.registrarVino(vino);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP actualizarVino(Vino vino) {
    ResultadoSP resultado = vinoRepository.actualizarVino(vino);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP eliminarVinoPorId(Integer idVino) {
    ResultadoSP resultado = vinoRepository.eliminarVinoPorId(idVino);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP filtrarVinoPorNombre(String nombre) {
    ResultadoSP resultado = vinoRepository.filtrarVinoPorNombre(nombre);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP filtrarVinosParaCompra(
      String nombre, String proveedores, String categorias,
      String presentaciones, String tiposVino, String origenVino) {
    ResultadoSP resultado = vinoRepository.filtrarVinosParaCompra(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino);
    ResponseVO.validar(resultado);
    return resultado;
  }

  // ─── Consultas simples ────────────────────────────────────────────────────

  @Override
  public List<VinoDTO> listarVinos() {
    return vinoRepository.listarVinos();
  }

  @Override
  public List<VinosCompraDTO> listarVinosParaCompra() {
    return vinoRepository.listarVinosParaCompra();
  }

  // ─── Consultas paginadas — arman PaginaResultado, el controller convierte a ResponseVO

  @Override
  public PaginaResultado<VinoDTO> listarVinosPaginados(int pagina, int limite) {
    List<VinoDTO> data = vinoRepository.listarVinosPaginados(pagina, limite);
    Long total = vinoRepository.contarVinos();
    int totalPaginas = (int) Math.ceil(total / (double) limite);
    return new PaginaResultado<>(data, pagina, limite, totalPaginas, total);
  }

  @Override
  public PaginaResultado<VinosCompraDTO> listarVinosParaCompraPaginados(int pagina, int limite) {
    List<VinosCompraDTO> data = vinoRepository.listarVinosParaCompraPaginados(pagina, limite);
    Long total = vinoRepository.contarVinosParaCompra();
    int totalPaginas = (int) Math.ceil(total / (double) limite);
    return new PaginaResultado<>(data, pagina, limite, totalPaginas, total);
  }


  @Override
  public PaginaResultado<VinosCompraDTO> filtrarVinosParaCompraPaginados(
      String nombre, String proveedores, String categorias,
      String presentaciones, String tiposVino, String origenVino,
      int pagina, int limite
  ) {
    // Una sola llamada al SP — pagina y cuenta sobre la misma lista
    List<VinosCompraDTO> todos = vinoRepository.filtrarVinosParaCompraPaginados(
        nombre, proveedores, categorias, presentaciones, tiposVino, origenVino, 1, Integer.MAX_VALUE);
    long total = todos.size();
    List<VinosCompraDTO> pagina_ = paginarEnMemoria(todos, pagina, limite);
    int totalPaginas = (int) Math.ceil(total / (double) limite);
    return new PaginaResultado<>(pagina_, pagina, limite, totalPaginas, total);
  }


  private <T> List<T> paginarEnMemoria(List<T> lista, int pagina, int limite) {
    int offset = (pagina - 1) * limite;
    if (offset >= lista.size()) return Collections.emptyList();
    return lista.subList(offset, Math.min(offset + limite, lista.size()));
  }

}
