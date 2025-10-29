package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}
