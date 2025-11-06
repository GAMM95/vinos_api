package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Proveedor;
import com.gamm.vinos_api.domain.view.ProveedorView;
import com.gamm.vinos_api.utils.ResultadoSP;

import java.util.List;

public interface ProveedorRepository {
  ResultadoSP registrarProveedor(Proveedor proveedor);

  ResultadoSP actualizarProveedor(Proveedor proveedor);

  ResultadoSP darDeBajaProveedor(Integer idProveedor);

  ResultadoSP darDeAltaProveedor(Integer idProveedor);

  ResultadoSP filtrarProveedor(String terminoBusqueda);

  List<ProveedorView> listarProveedores();
}
