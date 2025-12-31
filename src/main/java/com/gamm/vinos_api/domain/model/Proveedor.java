package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import com.gamm.vinos_api.domain.enums.OrigenVino;
import lombok.*;

@Data
public class Proveedor {
    private Integer idProveedor;
    private String codProveedor;
    private String razonSocial;
    private String ruc;
    private String contacto;
    private OrigenVino origen;
    private String ubicacion;
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}
