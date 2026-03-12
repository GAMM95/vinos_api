package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.VinoView;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.dto.view.VinosCompraView;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.VinoRepository;
import com.gamm.vinos_api.service.VinoService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VinoServiceImpl implements VinoService {

  @Autowired
  private VinoRepository vinoRepository;

  @Override
  public ResultadoSP registrarVino(Vino vino) {
    return vinoRepository.registrarVino(vino);
  }

  @Override
  public ResultadoSP actualizarVino(Vino vino) {
    return vinoRepository.actualizarVino(vino);
  }

  @Override
  public ResultadoSP eliminarVinoPorId(Integer idVino) {
    return vinoRepository.eliminarVinoPorId(idVino);
  }

  @Override
  public ResultadoSP filtrarVinoPorNombre(String nombre) {
    return vinoRepository.filtrarVinoPorNombre(nombre);
  }

  @Override
  public List<VinoView> listarVinos() {
    return vinoRepository.listarVinos();
  }

  @Override
  public ResponseVO listarVinosPaginados(int pagina, int limite) {
    // Obtener la lista de la pagina
    List<VinoView> vinosPagina = vinoRepository.listarVinosPaginados(pagina, limite);
    // Obtener el total de vinos
    Long totalVinos = vinoRepository.contarVinos();
    int totalPaginas = (int) Math.ceil(totalVinos / (double) limite);
    // Retornar usando ResponseVO
    return ResponseVO.paginated(vinosPagina, pagina, limite, totalPaginas, totalVinos);
  }

  @Override
  public List<VinosCompraView> listarVinosParaCompra() {
    return vinoRepository.listarVinosParaCompra();
  }

  @Override
  public ResponseVO listarVinosParaCompraPaginados(int pagina, int limite) {
    // Obtener la lista de la pagina
    List<VinosCompraView> vinosPagina = vinoRepository.listarVinosParaCompraPaginados(pagina, limite);
    // Obtener el total de vinos
    Long totalVinos = vinoRepository.contarVinosParaCompra();
    int totalPaginas = (int) Math.ceil(totalVinos / (double) limite);
    // Retornar usando ResponseVO
    return ResponseVO.paginated(vinosPagina, pagina, limite, totalPaginas, totalVinos);

  }

  @Override
  public ResultadoSP filtrarVinosParaCompra(String nombre, String proveedores, String categorias, String presentaciones, String tiposVino, String origenVino) {
    return vinoRepository.filtrarVinosParaCompra(nombre, proveedores, categorias, presentaciones, tiposVino, origenVino);
  }

  @Override
  public ResponseVO filtrarVinosParaCompraPaginados(
      String nombre,
      String proveedores,
      String categorias,
      String presentaciones,
      String tiposVino,
      String origenVino,
      int pagina,
      int limite) {

    // 1️⃣ Obtener los registros filtrados de la página
    List<VinosCompraView> vinosPagina = vinoRepository.filtrarVinosParaCompraPaginados(
        nombre,
        proveedores,
        categorias,
        presentaciones,
        tiposVino,
        origenVino,
        pagina,
        limite
    );

    // 2️⃣ Obtener el total de registros que cumplen el filtro
    Long totalVinos = vinoRepository.contarVinosParaCompraFiltrados(
        nombre,
        proveedores,
        categorias,
        presentaciones,
        tiposVino,
        origenVino
    );

    // 3️⃣ Calcular total de páginas
    int totalPaginas = (int) Math.ceil(totalVinos / (double) limite);

    // 4️⃣ Retornar ResponseVO paginado
    return ResponseVO.paginated(vinosPagina, pagina, limite, totalPaginas, totalVinos);
  }

  @Override
  public Long contarVinosParaCompraFiltrados(
      String nombre,
      String proveedores,
      String categorias,
      String presentaciones,
      String tiposVino,
      String origenVino) {

    // Simplemente delegamos al repo
    return vinoRepository.contarVinosParaCompraFiltrados(
        nombre,
        proveedores,
        categorias,
        presentaciones,
        tiposVino,
        origenVino
    );
  }

}
