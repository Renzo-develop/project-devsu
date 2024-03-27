package com.devsu.msusuario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@Entity
@Table(name="cliente")
public class Cliente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    @Column
    @NotNull
    private String contraseña;

    @Column
    @NotNull
    private Boolean estado;

    public Cliente() {

    }

}
