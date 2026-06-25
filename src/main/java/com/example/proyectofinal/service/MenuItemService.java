package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.MenuItemRequest;
import com.example.proyectofinal.entity.MenuItem;
import com.example.proyectofinal.enums.MenuCategory;
import com.example.proyectofinal.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> findByCategoria(MenuCategory categoria) {
        return menuItemRepository.findByCategoria(categoria);
    }

    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de menú no encontrado con id: " + id));
    }

    public MenuItem create(MenuItemRequest request) {
        MenuItem item = new MenuItem(request.getNombre(), request.getCategoria(), request.getDescripcion());
        return menuItemRepository.save(item);
    }

    public MenuItem update(Long id, MenuItemRequest request) {
        MenuItem item = findById(id);
        item.setNombre(request.getNombre());
        item.setCategoria(request.getCategoria());
        item.setDescripcion(request.getDescripcion());
        return menuItemRepository.save(item);
    }

    public void delete(Long id) {
        MenuItem item = findById(id);
        menuItemRepository.delete(item);
    }
}
