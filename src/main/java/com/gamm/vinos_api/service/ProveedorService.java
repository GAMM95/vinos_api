package com.gamm.vinos_api.service;

import com.gamm.vinos_api.domain.model.Proveedor;
import com.gamm.vinos_api.dto.view.ProveedorView;
import com.gamm.vinos_api.util.ResultadoSP;

import java.util.List;

public interface ProveedorService {
  ResultadoSP registrarProveedor(Proveedor proveedor);

  ResultadoSP actualizarProveedor(Proveedor proveedor);

  ResultadoSP darDeBajaProveedor(Integer idProveedor);

  ResultadoSP darDeAltaProveedor(Integer idProveedor);

  ResultadoSP filtrarProveedor(String terminoBusqueda);

  List<ProveedorView> listarProveedores();
}
