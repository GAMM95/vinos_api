package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.dto.queries.VinosCompraDTO;
import com.gamm.vinos_api.dto.view.VinoDTO;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface VinoRepository {

  // ─── CRUD ─────────────────────────────────────────────────────────────────
  ResultadoSP registrarVino(Vino vino);

  ResultadoSP actualizarVino(Vino vino);

  ResultadoSP eliminarVinoPorId(Integer idVino);

  // ─── Catálogo ─────────────────────────────────────────────────────────────

  List<VinoDTO> listarVinos();

  List<VinoDTO> listarVinosPaginados(int pagina, int limite);

  ResultadoSP filtrarVinoPorNombre(String nombre);

  Long contarVinos();

  List<VinosCompraDTO> listarVinosParaCompra();

  List<VinosCompraDTO> listarVinosParaCompraPaginados(int pagina, int limite);

  Long contarVinosParaCompra();

  ResultadoSP filtrarVinosParaCompra(String nombre, String proveedores, String categorias, String presentaciones, String tiposVino, String origenVino);

  List<VinosCompraDTO> filtrarVinosParaCompraPaginados(String nombre, String proveedores, String categorias, String presentaciones, String tiposVino, String origenVino, int pagina, int limite);

  Long contarVinosParaCompraFiltrados(String nombre, String proveedores, String categorias, String presentaciones, String tiposVino, String origenVino);

}
