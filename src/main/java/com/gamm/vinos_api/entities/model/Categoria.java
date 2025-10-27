package com.gamm.vinos_api.entities.model;

import com.gamm.vinos_api.entities.enums.EstadoRegistro;
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
