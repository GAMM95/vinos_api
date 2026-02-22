package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoCompra;
import com.gamm.vinos_api.domain.enums.MetodoPago;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    private Integer idCompra;
    private String codCompra;
    private LocalDateTime fechaCarrito;
    private LocalDateTime  fechaCompra;
    private BigDecimal costoEmbalaje;
    private BigDecimal costoEnvioAgencia;
    private BigDecimal costoTransporte;
    private BigDecimal totalCompra;
    private MetodoPago metodoPago;
    private String observaciones;
    private EstadoCompra estado = EstadoCompra.PENDIENTE;

    // Asociar carrito al usuario
    private Integer idUsuario;

    // Una compra puede tener 1 o varios detalles
    private List<DetalleCompra> detalles;
}

