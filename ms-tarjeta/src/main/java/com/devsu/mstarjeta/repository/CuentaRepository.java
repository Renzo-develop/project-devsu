package com.devsu.mstarjeta.repository;

import com.devsu.mstarjeta.entity.Cuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Long> {

    public List<Cuenta> findByClienteId(Long clienteId);
}
