package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalogo {
    private Integer idCatalogo;
    private Integer idProveedor;
    private Integer idVino;
    private Integer idPresentacion;
    private Double precioUnidad;
    private String observacion;
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}

