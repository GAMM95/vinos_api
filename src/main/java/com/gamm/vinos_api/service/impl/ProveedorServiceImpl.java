package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Proveedor;
import com.gamm.vinos_api.domain.view.ProveedorView;
import com.gamm.vinos_api.repository.ProveedorRepository;
import com.gamm.vinos_api.service.ProveedorService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {
  private final ProveedorRepository proveedorRepository;

  @Override
  public ResultadoSP registrarProveedor(Proveedor proveedor) {
    return proveedorRepository.registrarProveedor(proveedor);
  }

  @Override
  public ResultadoSP actualizarProveedor(Proveedor proveedor) {
    return proveedorRepository.actualizarProveedor(proveedor);
  }

  @Override
  public ResultadoSP darDeBajaProveedor(Integer idProveedor) {
    return proveedorRepository.darDeBajaProveedor(idProveedor);
  }

  @Override
  public ResultadoSP darDeAltaProveedor(Integer idProveedor) {
    return proveedorRepository.darDeAltaProveedor(idProveedor);
  }

  @Override
  public ResultadoSP filtrarProveedor(String terminoBusqueda) {
    return proveedorRepository.filtrarProveedor(terminoBusqueda);
  }

  @Override
  public List<ProveedorView> listarProveedores() {
    return proveedorRepository.listarProveedores();
  }
}
