package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Proveedor;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.ProveedorDTO;
import com.gamm.vinos_api.repository.ProveedorRepository;
import com.gamm.vinos_api.service.ProveedorService;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

  private final ProveedorRepository proveedorRepository;

  @Override
  public ResultadoSP registrarProveedor(Proveedor proveedor) {
    ResultadoSP resultado = proveedorRepository.registrarProveedor(proveedor);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP actualizarProveedor(Proveedor proveedor) {
    ResultadoSP resultado = proveedorRepository.actualizarProveedor(proveedor);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP darDeBajaProveedor(Integer idProveedor) {
    ResultadoSP resultado = proveedorRepository.darDeBajaProveedor(idProveedor);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP darDeAltaProveedor(Integer idProveedor) {
    ResultadoSP resultado = proveedorRepository.darDeAltaProveedor(idProveedor);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public ResultadoSP filtrarProveedor(String terminoBusqueda) {
    ResultadoSP resultado = proveedorRepository.filtrarProveedor(terminoBusqueda);
    ResponseVO.validar(resultado);
    return resultado;
  }

  @Override
  public List<ProveedorDTO> listarProveedores() {
    return proveedorRepository.listarProveedores();
  }
}
