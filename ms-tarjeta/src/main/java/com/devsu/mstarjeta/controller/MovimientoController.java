package com.devsu.mstarjeta.controller;

import com.devsu.libreria.ApiResponse;
import com.devsu.mstarjeta.entity.Cuenta;
import com.devsu.mstarjeta.entity.Movimiento;
import com.devsu.mstarjeta.entity.Reporte;
import com.devsu.mstarjeta.exception.InvalidBalanceException;
import com.devsu.mstarjeta.service.CuentaService;
import com.devsu.mstarjeta.service.MovimientoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService service;

    @CircuitBreaker(name="cbTarjeta", fallbackMethod = "fallbackCreateMovimiento")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMovimiento(@RequestBody Movimiento movimiento) {
        return ResponseEntity.ok()
                .body(service.createMovimiento(movimiento));
    }

    @CircuitBreaker(name="cbTarjeta", fallbackMethod = "fallbackCreateMovimiento")
    @PutMapping("/edit")
    public ResponseEntity<ApiResponse> editMovimiento(@RequestBody Movimiento movimiento) {
        return ResponseEntity.ok()
                .body(service.editMovimiento(movimiento));
    }

    @CircuitBreaker(name="cbTarjeta", fallbackMethod = "fallbackGetReports")
    @GetMapping("/reportes")
    public ResponseEntity<ApiResponse> reporteMovimientos(@RequestParam(name="fechainicial") String fechainicial,
                                                 @RequestParam(name="fechafinal") String fechafinal,
                                                 @RequestParam(name="clienteId") Long clienteId) {
        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Reporte de movimientos: "+ fechainicial + " al " + fechafinal)
                .response(service.reporteMovimientos(fechainicial, fechafinal, clienteId))
                .build());
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> listMovimientos() {
        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Listado de movimientos")
                .response(service.listMovimientos())
                .build());
    }

    @DeleteMapping("/delete/{clienteId}")
    public ResponseEntity<ApiResponse> deleteCuenta(@PathVariable Long cuentaId) {
        return ResponseEntity.ok()
                .body(service.deleteMovimiento(cuentaId));
    }

    private ResponseEntity<ApiResponse> fallbackCreateMovimiento(Movimiento movimiento, RuntimeException ex) {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Circuit Breaker - Reportes")
                        .response("El servicio Cliente no esta disponible ")
                        .build());
    }

    private ResponseEntity<ApiResponse> fallbackCreateMovimiento(Movimiento movimiento, InvalidBalanceException ex) {
        var response = ApiResponse.builder()
                .message("El saldo de la cuenta no puede ser negativo")
                .response(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse> fallbackGetReports(String fechainicial, String fechafinal, Long clienteId, RuntimeException ex) {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Circuit Breaker - Reportes")
                        .response("Problemas procesando el reporte ")
                        .build());
    }

}
