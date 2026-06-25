package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.MenuItem;
import com.example.proyectofinal.enums.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoria(MenuCategory categoria);
}
