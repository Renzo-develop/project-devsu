package com.devsu.mstarjeta.service;

import com.devsu.libreria.ApiResponse;
import com.devsu.mstarjeta.entity.Movimiento;
import com.devsu.mstarjeta.entity.Reporte;

import java.util.List;

public interface MovimientoService {
    public ApiResponse createMovimiento(Movimiento Movimiento);

    public ApiResponse editMovimiento(Movimiento Movimiento);

    public List<Movimiento> listMovimientos();

    public List<Reporte> reporteMovimientos(String fechainicio, String fechafinal, Long clienteid);

    public ApiResponse deleteMovimiento(Long id);

}
