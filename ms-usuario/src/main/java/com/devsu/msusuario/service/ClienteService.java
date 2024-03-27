package com.devsu.msusuario.service;

import com.devsu.msusuario.entity.ApiResponse;
import com.devsu.msusuario.entity.Cliente;
import com.devsu.msusuario.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ClienteService {

    public Cliente createCliente(Cliente cliente);

    public ApiResponse editCliente(Cliente cliente);

    public ApiResponse findById(Long clienteId);

    public Cliente getClientById(Long clienteId);

    public List<Cliente> listClientes();

    public ApiResponse deleteCliente(Long id);
}
