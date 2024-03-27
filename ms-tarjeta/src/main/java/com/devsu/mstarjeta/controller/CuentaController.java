package com.devsu.mstarjeta.controller;

import com.devsu.libreria.ApiResponse;
import com.devsu.mstarjeta.entity.Cuenta;
import com.devsu.mstarjeta.service.CuentaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService service;

    @CircuitBreaker(name="cbTarjeta", fallbackMethod = "fallbackCreateCuenta")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCuenta(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok()
                .body(service.createCuenta(cuenta));
    }

    @CircuitBreaker(name="cbTarjeta", fallbackMethod = "fallbackCreateCuenta")
    @PutMapping("/edit")
    public ResponseEntity<ApiResponse> editCuenta(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok()
                .body(service.editCuenta(cuenta));
    }

    @GetMapping("/findbyid/{cuentaId}")
    public ResponseEntity<ApiResponse> listCuenta(@PathVariable Long cuentaId) {
        return null;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> listCuentas() {
        return ResponseEntity.ok().body(ApiResponse.builder()
                .message("Listado de cuentas")
                .response(service.listCuentas())
                .build());
    }

    @DeleteMapping("/delete/{clienteId}")
    public ResponseEntity<ApiResponse> deleteCuenta(@PathVariable Long cuentaId) {
        return ResponseEntity.ok()
                .body(service.deleteCuenta(cuentaId));
    }

    private ResponseEntity<ApiResponse> fallbackCreateCuenta(Cuenta cuentaId, WebClientRequestException ex) {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Circuit Breaker")
                        .response("El servicio Cliente no esta disponible")
                        .build());
    }

}
