package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import lombok.*;

@Data
public class Proveedor {
    private Integer idProveedor;
    private String codProveedor;
    private String razonSocial;
    private String ruc;
    private String contacto;
    private String ubicacion;
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}
