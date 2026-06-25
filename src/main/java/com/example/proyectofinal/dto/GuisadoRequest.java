package com.example.proyectofinal.dto;

import com.example.proyectofinal.enums.GuisadoCategory;

public class GuisadoRequest {
    private String nombre;
    private GuisadoCategory categoria;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public GuisadoCategory getCategoria() { return categoria; }
    public void setCategoria(GuisadoCategory categoria) { this.categoria = categoria; }
}
