package com.devsu.mstarjeta.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Reporte {

    private LocalDate fecha;

    private String clienteName;

    private String numeroCuenta;

    private Cuenta.TypeCuenta tipoCuenta;

    private Double saldoInicial;

    private Double valorMovimiento;

    private Double saldoDisponible;


}
