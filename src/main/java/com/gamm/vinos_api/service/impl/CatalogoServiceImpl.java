package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.dto.view.CatalogoView;
import com.gamm.vinos_api.domain.model.Catalogo;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.repository.CatalogoRepository;
import com.gamm.vinos_api.service.CatalogoService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {
  private final CatalogoRepository catalogoRepository;

  @Override
  public ResultadoSP registrarCatalogo(Catalogo catalogo) {
    return catalogoRepository.registrarCatalogo(catalogo);
  }

  @Override
  public ResultadoSP actualizarCatalogo(Catalogo catalogo) {
    return catalogoRepository.actualizarCatalogo(catalogo);
  }

  @Override
  public ResultadoSP darDeBajaCatalogo(Integer idCatalogo) {
    return catalogoRepository.darDeBajaCatalogo(idCatalogo);
  }

  @Override
  public ResultadoSP darDeAltaCatalogo(Integer idCatalogo) {
    return catalogoRepository.darDeAltaCatalogo(idCatalogo);
  }

  @Override
  public List<CatalogoView> listarCatalogos() {
    return catalogoRepository.listarCatalogos();
  }

  @Override
  public ResponseVO listarCatalogosPaginadosPorProveedor(Integer idProveedor, int pagina, int limite) {
    // Obtener la lista de la pagina
    List<CatalogoView> catalogosPagina = catalogoRepository.listarCatalogosPaginados(idProveedor, pagina, limite);
    // Obtener el total de registros para calcular paginas
    Long totalRegistros = catalogoRepository.contarCatalogos(idProveedor);
    int totalPaginas = (int) Math.ceil(totalRegistros / (double) limite);

    // Retornar usando ResponseVO con paginacion
    return ResponseVO.paginated(
        catalogosPagina, //data
        pagina, // pagina actual
        limite, // registros por pagina
        totalPaginas, // total de paginas
        totalRegistros // total de registros
    );
  }


  @Override
  public ResultadoSP filtrarPorProveedor(Integer idProveedor) {
    return catalogoRepository.filtrarPorProveedor(idProveedor);
  }

//  @Override
//  public ResultadoSP filtrarPorProveedorYTermino(Integer idProveedor, String termino) {
//    return catalogoRepository.filtrarPorIDProveedorTermino(idProveedor, termino);
//  }
}
