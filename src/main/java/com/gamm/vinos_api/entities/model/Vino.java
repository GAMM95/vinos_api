package com.gamm.vinos_api.entities.model;

import com.gamm.vinos_api.entities.enums.EstadoRegistro;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vino {
    private Integer idVino;
    private String nombre;
    private Integer idCategoria;
    private Double precioVenta;
    private String descripcion;
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;

}
