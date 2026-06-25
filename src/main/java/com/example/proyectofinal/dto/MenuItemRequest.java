package com.example.proyectofinal.dto;

import com.example.proyectofinal.enums.MenuCategory;

public class MenuItemRequest {
    private String nombre;
    private MenuCategory categoria;
    private String descripcion;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public MenuCategory getCategoria() { return categoria; }
    public void setCategoria(MenuCategory categoria) { this.categoria = categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
