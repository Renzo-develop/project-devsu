package com.devsu.libreria;

import lombok.Data;

@Data
public class Persona {

    private String nombre;

    private TypeGenero genero;

    private Integer edad;

    private Long identificacion;

    private String direccion;

    private Long telefono;

    public Persona() {

    }

    public static enum TypeGenero {
        M, F
    }
}
