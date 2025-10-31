package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.view.CompraView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface CompraRepository {
  ResultadoSP crearCompra(Compra compra);

  ResultadoSP confirmarCompra(Compra compra);

  ResultadoSP actualizarCompra(Compra compra);

  ResultadoSP anularCompra(Integer idCompra);

  ResultadoSP reactivarCompra(Integer idCompra);

  ResultadoSP buscarCompras(Compra compra);

  List<CompraView> listarCompras();
}
