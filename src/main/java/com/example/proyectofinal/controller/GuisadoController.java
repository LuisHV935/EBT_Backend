package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.GuisadoRequest;
import com.example.proyectofinal.entity.Guisado;
import com.example.proyectofinal.enums.GuisadoCategory;
import com.example.proyectofinal.service.GuisadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guisados")
@Tag(name = "Guisados", description = "CRUD de guisados para taquiza")
public class GuisadoController {

    private final GuisadoService guisadoService;

    public GuisadoController(GuisadoService guisadoService) {
        this.guisadoService = guisadoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los guisados")
    public ResponseEntity<List<Guisado>> findAll() {
        return ResponseEntity.ok(guisadoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener guisado por ID")
    public ResponseEntity<Guisado> findById(@PathVariable Long id) {
        return ResponseEntity.ok(guisadoService.findById(id));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Filtrar guisados por categoría")
    public ResponseEntity<List<Guisado>> findByCategoria(@PathVariable GuisadoCategory categoria) {
        return ResponseEntity.ok(guisadoService.findByCategoria(categoria));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo guisado")
    public ResponseEntity<Guisado> create(@RequestBody GuisadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guisadoService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un guisado")
    public ResponseEntity<Guisado> update(@PathVariable Long id, @RequestBody GuisadoRequest request) {
        return ResponseEntity.ok(guisadoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un guisado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        guisadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
