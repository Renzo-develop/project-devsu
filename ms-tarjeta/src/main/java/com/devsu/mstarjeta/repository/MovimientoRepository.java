package com.devsu.mstarjeta.repository;

import com.devsu.mstarjeta.entity.Cuenta;
import com.devsu.mstarjeta.entity.Movimiento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends CrudRepository<Movimiento, Long> {
    public List<Movimiento> findByCuentaId(Long clienteId);
}
