package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoCompra;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    private Integer idCompra;
    private String codCompra;
    private Integer idProveedor;
    private Date fecha;
    private Double costoEmbalaje;
    private Double costoEnvioAgencia;
    private Double costoTransporte;
    private Double totalCompra;
    private String observaciones;
    private EstadoCompra estado = EstadoCompra.PENDIENTE;
}

