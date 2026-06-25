package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.MenuItemRequest;
import com.example.proyectofinal.entity.MenuItem;
import com.example.proyectofinal.enums.MenuCategory;
import com.example.proyectofinal.service.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@Tag(name = "Menú", description = "CRUD de items del menú de 3 tiempos y plato volado")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los items del menú")
    public ResponseEntity<List<MenuItem>> findAll() {
        return ResponseEntity.ok(menuItemService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener item por ID")
    public ResponseEntity<MenuItem> findById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.findById(id));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Filtrar items por categoría")
    public ResponseEntity<List<MenuItem>> findByCategoria(@PathVariable MenuCategory categoria) {
        return ResponseEntity.ok(menuItemService.findByCategoria(categoria));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo item")
    public ResponseEntity<MenuItem> create(@RequestBody MenuItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un item")
    public ResponseEntity<MenuItem> update(@PathVariable Long id, @RequestBody MenuItemRequest request) {
        return ResponseEntity.ok(menuItemService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un item")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
