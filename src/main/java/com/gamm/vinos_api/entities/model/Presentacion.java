package com.gamm.vinos_api.entities.model;

import com.gamm.vinos_api.entities.enums.EstadoRegistro;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Presentacion {
    private Integer idPresentacion;
    private String nombre;
    private Double litrosUnidad;
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}
