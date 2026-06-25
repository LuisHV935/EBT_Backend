package com.example.proyectofinal.entity;

import com.example.proyectofinal.enums.MenuCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuCategory categoria;

    @Column(length = 500)
    private String descripcion;

    public MenuItem() {}

    public MenuItem(String nombre, MenuCategory categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public MenuItem(String nombre, MenuCategory categoria, String descripcion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public MenuCategory getCategoria() { return categoria; }
    public void setCategoria(MenuCategory categoria) { this.categoria = categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
