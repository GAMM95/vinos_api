package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.common.PaginaResultado;
import com.gamm.vinos_api.dto.queries.VinosCompraDTO;
import com.gamm.vinos_api.dto.view.VinoDTO;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface VinoService {
  // ─── Mutaciones ───────────────────────────────────────────────────────────
  ResultadoSP registrarVino(Vino vino);

  ResultadoSP actualizarVino(Vino vino);

  ResultadoSP eliminarVinoPorId(Integer idVino);

  ResultadoSP filtrarVinoPorNombre(String nombre);

  ResultadoSP filtrarVinosParaCompra(String nombre, String proveedores, String categorias,
                                     String presentaciones, String tiposVino, String origenVino);

  // ─── Consultas simples ────────────────────────────────────────────────────
  List<VinoDTO> listarVinos();

  List<VinosCompraDTO> listarVinosParaCompra();

  // ─── Consultas paginadas — devuelven PaginaResultado, el controller arma ResponseVO
  PaginaResultado<VinoDTO> listarVinosPaginados(int pagina, int limite);

  PaginaResultado<VinosCompraDTO> listarVinosParaCompraPaginados(int pagina, int limite);

  PaginaResultado<VinosCompraDTO> filtrarVinosParaCompraPaginados(
      String nombre, String proveedores, String categorias,
      String presentaciones, String tiposVino, String origenVino,
      int pagina, int limite
  );
}
