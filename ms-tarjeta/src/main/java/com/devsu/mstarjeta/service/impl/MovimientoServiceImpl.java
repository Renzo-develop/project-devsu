package com.devsu.mstarjeta.service.impl;

import com.devsu.libreria.ApiResponse;
import com.devsu.libreria.Cliente;
import com.devsu.mstarjeta.entity.Cuenta;
import com.devsu.mstarjeta.entity.Movimiento;
import com.devsu.mstarjeta.entity.Reporte;
import com.devsu.mstarjeta.exception.InvalidBalanceException;
import com.devsu.mstarjeta.repository.CuentaRepository;
import com.devsu.mstarjeta.repository.MovimientoRepository;
import com.devsu.mstarjeta.service.CuentaService;
import com.devsu.mstarjeta.service.MovimientoService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MovimientoRepository movimientoRepository;

    private WebClient clienteRepository = WebClient.create("http://msusuario-devsu:8080/clientes");

    private static final DateTimeFormatter YEAR_MONTH_DAY = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    @Transactional
    public ApiResponse createMovimiento(Movimiento movimiento) {

        return cuentaRepository.findById(movimiento.getCuentaId())
                .map(cuenta -> {
                    Movimiento movimientoProcessed = processMovimiento(movimiento, cuenta);
                    cuenta.setSaldo(movimientoProcessed.getValor() + cuenta.getSaldo());
                    cuentaService.editCuenta(cuenta);
                    return ApiResponse.builder()
                            .message("Movimiento registrado")
                            .response(movimientoRepository.save(movimientoProcessed))
                            .build();
                })
                .orElse(ApiResponse.builder()
                        .message("No existe un movimiento con ese identificador")
                        .response(Strings.EMPTY)
                        .build());
    }

    @Override
    public ApiResponse editMovimiento(Movimiento movimiento) {
        return movimientoRepository.findById(movimiento.getMovimientoId())
                .map(movimientoDB -> fillMovimiento(movimientoDB, movimiento))
                .map(movimientoFilled -> movimientoRepository.save(movimientoFilled))
                .map(movimientoUpdated -> ApiResponse.builder()
                        .message("Movimiento actualizado")
                        .response(movimientoUpdated)
                        .build())
                .orElse(ApiResponse.builder()
                        .message("No existe un movimiento con ese identificador")
                        .response(Strings.EMPTY)
                        .build());
    }

    @Override
    public List<Movimiento> listMovimientos() {
        List<Movimiento> list = new ArrayList<>();
        movimientoRepository.findAll().forEach(list::add);
        return list;
    }

    public List<Reporte> reporteMovimientos(String fechainicio, String fechafinal, Long clienteid) {
        List<Reporte> reportes = new ArrayList<>();
        LocalDate finicio = LocalDate.parse(fechainicio, YEAR_MONTH_DAY);
        LocalDate ffinal = LocalDate.parse(fechafinal, YEAR_MONTH_DAY);

        Cliente cliente = clienteRepository.get().uri("/getclientbyid/{id}", clienteid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Cliente.class).block();

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteid);

        cuentas.stream()
                .forEach(cuenta -> {
                    List<Movimiento> movimientos = movimientoRepository.findByCuentaId(cuenta.getCuentaId());
                    movimientos.stream().forEach(
                            movimiento -> {
                                if(movimiento.getFecha().isAfter(finicio) && movimiento.getFecha().isBefore(ffinal) ||
                                   movimiento.getFecha().isEqual(finicio) || movimiento.getFecha().isEqual(ffinal)) {
                                    Reporte reporte = Reporte.builder()
                                            .fecha(movimiento.getFecha())
                                            .clienteName(cliente.getNombre())
                                            .numeroCuenta(cuenta.getNumeroCuenta())
                                            .tipoCuenta(cuenta.getTipoCuenta())
                                            .saldoInicial(movimiento.getSaldo())
                                            .valorMovimiento(movimiento.getValor())
                                            .saldoDisponible(cuenta.getSaldo())
                                            .build();
                                    reportes.add(reporte);
                                }
                            }
                    );
                });

        return reportes;
    }

    @Override
    public ApiResponse deleteMovimiento(Long id) {
        return movimientoRepository.findById(id)
                .map(movimiento -> ApiResponse.builder()
                        .message("Movimiento eliminado")
                        .response(Strings.EMPTY)
                        .build())
                .orElse(ApiResponse.builder()
                        .message("No existe un movimiento con ese identificador")
                        .response(Strings.EMPTY)
                        .build());
    }

    private Movimiento processMovimiento(Movimiento movimiento, Cuenta cuenta) {
        movimiento.setSaldo(cuenta.getSaldo());
        if(movimiento.getValor() > 0) {
            movimiento.setTipoMovimientos(Movimiento.TypeMovimiento.DEPOSITO);
        } else {
            movimiento.setTipoMovimientos(Movimiento.TypeMovimiento.RETIRO);
        }
        movimiento.setFecha(Optional.ofNullable(movimiento.getFecha()).orElse(LocalDate.now()));

        if(movimiento.getSaldo() + movimiento.getValor() < 0) {
            throw new InvalidBalanceException("Saldo disponible: "+ cuenta.getSaldo());
        }

        return  movimiento;
    }

    private Movimiento fillMovimiento(Movimiento original, Movimiento updated) {

        updated.setMovimientoId(original.getMovimientoId());
        updated.setCuentaId(original.getCuentaId());
        updated.setFecha(Optional.ofNullable(updated.getFecha()).orElse(original.getFecha()));
        updated.setTipoMovimientos(original.getTipoMovimientos());
        updated.setValor(original.getValor());
        updated.setSaldo(original.getSaldo());

        return updated;
    }
}