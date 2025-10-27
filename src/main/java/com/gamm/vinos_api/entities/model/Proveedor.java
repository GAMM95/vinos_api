package com.gamm.vinos_api.entities.model;

import com.gamm.vinos_api.entities.enums.EstadoRegistro;
import lombok.*;

@Data
public class Proveedor {
    private Integer idProveedor;
    private String codProveedor;
    private String nombre;
    private String contacto;
    private String ubicacion;
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;

}
