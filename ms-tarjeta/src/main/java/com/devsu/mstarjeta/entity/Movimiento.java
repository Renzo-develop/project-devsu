package com.devsu.mstarjeta.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="movimiento")
public class Movimiento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;

    @Column
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;

    @Column
    private TypeMovimiento tipoMovimientos;

    @Column
    @NotNull
    private Double valor;

    @Column
    private Double saldo;

    @Column
    @NotNull
    private Long cuentaId;

    public enum TypeMovimiento {
        RETIRO, DEPOSITO
    }
}
