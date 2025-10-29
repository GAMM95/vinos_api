package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
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
