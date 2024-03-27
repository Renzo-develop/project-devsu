package com.devsu.msusuario.controller;

import com.devsu.msusuario.entity.ApiResponse;
import com.devsu.msusuario.entity.Cliente;
import com.devsu.msusuario.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Cliente creado OK")
                        .response(service.createCliente(cliente))
                        .build());
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse> editCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok()
                .body(service.editCliente(cliente));
    }

    @GetMapping("/findbyid/{clienteId}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long clienteId) {
        return ResponseEntity.ok()
                .body(service.findById(clienteId));
    }

    @GetMapping("/getclientbyid/{clienteId}")
    public Cliente getClientById(@PathVariable Long clienteId) {
        return service.getClientById(clienteId);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> listClientes() {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Listado de clientes")
                        .response(service.listClientes())
                        .build());
    }

    @DeleteMapping("/delete/{clienteId}")
    public ResponseEntity<ApiResponse> deleteCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok()
                .body(service.deleteCliente(clienteId));
    }

}
