package com.gamm.vinos_api.service;


import com.gamm.vinos_api.domain.view.VinoView;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.domain.view.VinosCompraView;
import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface VinoService {
  ResultadoSP registrarVino(Vino vino);

  ResultadoSP actualizarVino(Vino vino);

  ResultadoSP eliminarVinoPorId(Integer idVino);

  ResultadoSP filtrarVinoPorNombre(String nombre);

  List<VinoView> listarVinos();

  ResponseVO listarVinosPaginados(int pagina, int limite);

  List<VinosCompraView> listarVinosParaCompra();

  ResponseVO listarVinosParaCompraPaginados(int pagina, int limite);

  ResultadoSP filtrarVinosParaCompra(String nombre, String proveedores, String categorias, String presentaciones, String tiposVino, String origenVino);

  // Nuevo filtrado paginado y conteo
  ResponseVO filtrarVinosParaCompraPaginados(
      String nombre,
      String proveedores,
      String categorias,
      String presentaciones,
      String tiposVino,
      String origenVino,
      int pagina,
      int limite
  );

  // 🔹 Conteo filtrado (opcional si quieres total de registros para paginación)
  Long contarVinosParaCompraFiltrados(
      String nombre,
      String proveedores,
      String categorias,
      String presentaciones,
      String tiposVino,
      String origenVino
  );
}
