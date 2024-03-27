package com.devsu.msusuario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@MappedSuperclass
public class Persona {

    @Column
    @NotNull
    private String nombre;

    @Column
    @NotNull
    private TypeGenero genero;

    @Column
    @NotNull
    private Integer edad;

    @Column(unique=true)
    @NotNull
    private Long identificacion;

    @Column
    @NotNull
    private String direccion;

    @Column
    @NotNull
    private Long telefono;

    public Persona() {

    }

    public static enum TypeGenero {
        M, F
    }
}
