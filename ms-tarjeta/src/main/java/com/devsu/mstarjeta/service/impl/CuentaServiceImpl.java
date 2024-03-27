package com.devsu.mstarjeta.service.impl;

import com.devsu.libreria.ApiResponse;
import com.devsu.libreria.Cliente;
import com.devsu.mstarjeta.entity.Cuenta;
import com.devsu.mstarjeta.repository.CuentaRepository;
import com.devsu.mstarjeta.service.CuentaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    private WebClient clienteRepository = WebClient.create("http://msusuario-devsu:8080/clientes");

    @Override
    public ApiResponse createCuenta(Cuenta cuenta) {

        return clienteRepository.get().uri("/findbyid/{id}", cuenta.getClienteId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .filter(response -> Strings.isNotBlank(response.getResponse().toString()))
                .map(response -> ApiResponse.builder()
                        .message("Cuenta creada OK")
                        .response(cuentaRepository.save(processCuenta(cuenta)))
                        .build())
                .switchIfEmpty(Mono.just(ApiResponse.builder()
                        .message("Cliente ingresado no existe")
                        .response(Strings.EMPTY)
                        .build())).block();
    }

    @Override
    public ApiResponse editCuenta(Cuenta cuenta) {
        return cuentaRepository.findById(cuenta.getCuentaId())
                .map(cuentaDB -> fillCuenta(cuentaDB, cuenta))
                .map(cuentaFilled -> cuentaRepository.save(cuentaFilled))
                .map(cuentaUpdated -> ApiResponse.builder()
                        .message("Cuenta actualizada")
                        .response(cuentaUpdated)
                        .build())
                .orElse(ApiResponse.builder()
                        .message("Cuenta no encontrada")
                        .response(Strings.EMPTY)
                        .build());
    }

    @Override
    public List<Cuenta> listCuentas() {
        List<Cuenta> list = new ArrayList<>();
        cuentaRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public ApiResponse deleteCuenta(Long id) {
        return cuentaRepository.findById(id)
                .map(cuenta -> ApiResponse.builder()
                        .message("Cuenta eliminada")
                        .response(Strings.EMPTY)
                        .build())
                .orElse(ApiResponse.builder()
                        .message("Cuenta no encontrada")
                        .response(Strings.EMPTY)
                        .build());
    }

    private Cuenta processCuenta(Cuenta cuenta) {
        cuenta.setSaldo(Optional.ofNullable(cuenta.getSaldo()).orElse(0.0));
        return cuenta;
    }

    public Cuenta fillCuenta(Cuenta original, Cuenta updated) {
        return Cuenta.builder()
                .cuentaId(original.getCuentaId())
                .clienteId(original.getClienteId())
                .numeroCuenta(original.getNumeroCuenta())
                .tipoCuenta(Optional.ofNullable(updated.getTipoCuenta()).orElse(original.getTipoCuenta()))
                .saldo(Optional.ofNullable(updated.getSaldo()).orElse(original.getSaldo()))
                .estado(Optional.ofNullable(updated.getEstado()).orElse(original.getEstado()))
                .build();
    }
}
