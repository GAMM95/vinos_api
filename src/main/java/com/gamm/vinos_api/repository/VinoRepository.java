package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.view.VinoView;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.domain.view.VinosCompraView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface VinoRepository {
  ResultadoSP registrarVino(Vino vino);

  ResultadoSP actualizarVino(Vino vino);

  ResultadoSP eliminarVinoPorId(Integer idVino);

  ResultadoSP filtrarVinoPorNombre(String nombre);

  List<VinoView> listarVinos();

  List<VinoView> listarVinosPaginados(int pagina, int limite);

  Long contarVinos();

  List<VinosCompraView> listarVinosParaCompra();

  List<VinosCompraView> listarVinosParaCompraPaginados(int pagina, int limite);

  Long contarVinosParaCompra();

  ResultadoSP filtrarVinosParaCompra(String nombre, String proveedores, String categorias, String presentaciones, String tiposVino, String origenVino);

  List<VinosCompraView> filtrarVinosParaCompraPaginados(String nombre, String proveedores, String categorias, String presentaciones, String tiposVino, String origenVino, int pagina, int limite);

  Long contarVinosParaCompraFiltrados(String nombre, String proveedores, String categorias, String presentaciones, String tiposVino, String origenVino);

}
