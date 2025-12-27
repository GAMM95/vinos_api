package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoVino;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vino {
    private Integer idVino;
    private String nombre;
    private Integer idCategoria;
    private String descripcion;
    private EstadoVino estado = EstadoVino.DISPONIBLE;
}
