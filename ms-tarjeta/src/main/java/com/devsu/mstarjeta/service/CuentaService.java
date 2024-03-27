package com.devsu.mstarjeta.service;

import com.devsu.libreria.ApiResponse;
import com.devsu.mstarjeta.entity.Cuenta;

import java.util.List;

public interface CuentaService {
    public ApiResponse createCuenta(Cuenta cuenta);

    public ApiResponse editCuenta(Cuenta cuenta);

    public List<Cuenta> listCuentas();

    public ApiResponse deleteCuenta(Long id);
}