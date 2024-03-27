package com.devsu.mstarjeta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@AllArgsConstructor
@Table(name="cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;

    @Column(unique=true)
    @NotNull
    private String numeroCuenta;

    @Column
    @NotNull
    private TypeCuenta tipoCuenta;

    @Column
    @NotNull
    private Double saldo;

    @Column
    @NotNull
    private Boolean estado;

    @Column
    @NotNull
    private Long clienteId;

    public enum TypeCuenta {
        AHORROS, CORRIENTE
    }

    public Cuenta() {

    }
}