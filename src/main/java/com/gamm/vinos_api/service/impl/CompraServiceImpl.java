package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Compra;
import com.gamm.vinos_api.domain.view.CompraView;
import com.gamm.vinos_api.repository.CompraRepository;
import com.gamm.vinos_api.service.CompraService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

  private final CompraRepository compraRepository;

  @Override
  public ResultadoSP crearCompra(Compra compra) {
    return compraRepository.crearCompra(compra);
  }

  @Override
  public ResultadoSP confirmarCompra(Compra compra) {
    return compraRepository.confirmarCompra(compra);
  }

  @Override
  public ResultadoSP actualizarCompra(Compra compra) {
    return compraRepository.actualizarCompra(compra);
  }

  @Override
  public ResultadoSP anularCompra(Integer idCompra) {
    return compraRepository.anularCompra(idCompra);
  }

  @Override
  public ResultadoSP reactivarCompra(Integer idCompra) {
    return compraRepository.reactivarCompra(idCompra);
  }

  @Override
  public ResultadoSP buscarCompras(Compra compra) {
    return compraRepository.buscarCompras(compra);
  }

  @Override
  public List<CompraView> listarCompras() {
    return compraRepository.listarCompras();
  }
}
