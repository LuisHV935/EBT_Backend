package com.example.proyectofinal.entity;

import com.example.proyectofinal.enums.GuisadoCategory;
import jakarta.persistence.*;

@Entity
public class Guisado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GuisadoCategory categoria;

    public Guisado() {}

    public Guisado(String nombre, GuisadoCategory categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public GuisadoCategory getCategoria() { return categoria; }
    public void setCategoria(GuisadoCategory categoria) { this.categoria = categoria; }
}
