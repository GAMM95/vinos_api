package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoCompra;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    private Integer idCompra;
    private Integer idProveedor;
    private Double costoEmbalaje;
    private Double costoEnvioAgencia;
    private Double costoTransporte;
    private String observaciones;
    private Integer idCatalogo;
    private Integer cantidadGalones;
    private String nombreProveedor;
    private LocalDate  fechaInicio;
    private LocalDate  fechaFin;
    private EstadoCompra estado = EstadoCompra.PENDIENTE;
}

