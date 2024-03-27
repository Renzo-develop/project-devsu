package com.devsu.libreria;

import lombok.Data;

@Data
public class Cliente extends Persona {
    private Long clienteId;

    private String contraseña;

    private Boolean estado;

    public Cliente() {

    }

}
