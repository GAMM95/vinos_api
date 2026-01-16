package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
import com.gamm.vinos_api.domain.enums.TipoVino;
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
    private TipoVino tipoVino;
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}

