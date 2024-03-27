package com.devsu.msusuario.service.impl;

import com.devsu.msusuario.entity.ApiResponse;
import com.devsu.msusuario.entity.Cliente;
import com.devsu.msusuario.repository.ClienteRepository;
import com.devsu.msusuario.service.ClienteService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    @Transactional
    public Cliente createCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public ApiResponse editCliente(Cliente cliente) {
        return repository.findById(cliente.getClienteId())
                .map(clienteDB -> fillCliente(clienteDB, cliente))
                .map(clientFilled -> repository.save(clientFilled))
                .map(clientUpdated -> ApiResponse.builder()
                        .message("Cliente actualizado")
                        .response(clientUpdated)
                        .build())
                .orElse(ApiResponse.builder()
                            .message("Cliente no encontrado")
                            .response(Strings.EMPTY)
                            .build());
    }

    public ApiResponse findById(Long clienteId) {
        return repository.findById(clienteId)
                .map(cliente -> ApiResponse.builder()
                        .message("Cliente encontrado")
                        .response(cliente)
                        .build())
                .orElse(ApiResponse.builder()
                        .message("Cliente no existe")
                        .response(Strings.EMPTY)
                        .build());
    }

    @Override
    public Cliente getClientById(Long clienteId) {
        return repository.findById(clienteId).get();
    }

    @Override
    public List<Cliente> listClientes() {
        List<Cliente> list = new ArrayList<>();
        repository.findAll().forEach(c -> list.add(c));
        return list;
    }

    @Override
    public ApiResponse deleteCliente(Long id) {
        return repository.findById(id)
                .map(cliente -> ApiResponse.builder()
                                .message("Cliente eliminado")
                                .response(Strings.EMPTY)
                                .build())
                .orElse(ApiResponse.builder()
                        .message("Cliente no encontrado")
                        .response(Strings.EMPTY)
                        .build());
    }

    public Cliente fillCliente(Cliente original, Cliente updated) {
        return Cliente.builder()
                .clienteId(original.getClienteId())
                .nombre(Optional.ofNullable(updated.getNombre()).orElse(original.getNombre()))
                .genero(Optional.ofNullable(updated.getGenero()).orElse(original.getGenero()))
                .edad(Optional.ofNullable(updated.getEdad()).orElse(original.getEdad()))
                .edad(Optional.ofNullable(updated.getEdad()).orElse(original.getEdad()))
                .identificacion(Optional.ofNullable(updated.getIdentificacion()).orElse(original.getIdentificacion()))
                .direccion(Optional.ofNullable(updated.getDireccion()).orElse(original.getDireccion()))
                .telefono(Optional.ofNullable(updated.getTelefono()).orElse(original.getTelefono()))
                .contraseña(Optional.ofNullable(updated.getContraseña()).orElse(original.getContraseña()))
                .estado(Optional.ofNullable(updated.getEstado()).orElse(original.getEstado()))
                .build();
    }

}
