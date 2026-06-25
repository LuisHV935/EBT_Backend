package com.example.proyectofinal.service;

import com.example.proyectofinal.entity.Guisado;
import com.example.proyectofinal.entity.MenuItem;
import com.example.proyectofinal.entity.Salsa;
import com.example.proyectofinal.enums.GuisadoCategory;
import com.example.proyectofinal.enums.MenuCategory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CatalogoService {

    private final GuisadoService guisadoService;
    private final MenuItemService menuItemService;
    private final SalsaService salsaService;

    public CatalogoService(GuisadoService guisadoService,
                           MenuItemService menuItemService,
                           SalsaService salsaService) {
        this.guisadoService = guisadoService;
        this.menuItemService = menuItemService;
        this.salsaService = salsaService;
    }

    public Map<String, Object> getCatalogo() {

        List<Map<String, Object>> taquizaCategorias = new ArrayList<>();
        for (GuisadoCategory cat : GuisadoCategory.values()) {
            List<Guisado> items = guisadoService.findByCategoria(cat);
            Map<String, Object> categoria = new LinkedHashMap<>();
            categoria.put("nombre", cat.name());
            categoria.put("items", items.stream().map(g -> {
                Map<String, Object> itemMap = new LinkedHashMap<>();
                itemMap.put("id", g.getId());
                itemMap.put("nombre", g.getNombre());
                return itemMap;
            }).toList());
            taquizaCategorias.add(categoria);
        }

        Map<String, Object> taquiza = new LinkedHashMap<>();
        taquiza.put("tipo", "TAQUIZA");
        taquiza.put("descripcion", "4-5 guisados a elección del cliente. Incluye arroz, frijoles, 2 salsas (roja y verde) y agua fresca. No hay límite de tacos por persona.");
        taquiza.put("precioReferencia", "$110 por persona (consultar para eventos especiales)");
        taquiza.put("incluye", List.of("Arroz", "Frijoles", "Salsa roja", "Salsa verde", "Agua fresca"));
        taquiza.put("categorias", taquizaCategorias);

        Map<String, Object> tresTiempos = buildMenuService("TRES_TIEMPOS",
                "Crema + pasta + plato fuerte + ensalada + guarnición",
                true);

        Map<String, Object> platoVolado = buildMenuService("PLATO_VOLADO",
                "Pasta + plato fuerte + ensalada + guarnición (no incluye cremas)",
                false);

        List<Map<String, Object>> salsas = salsaService.findAll().stream()
                .map(s -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", s.getId());
                    map.put("nombre", s.getNombre());
                    map.put("tipo", s.getTipo());
                    return map;
                })
                .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("servicios", List.of(taquiza, tresTiempos, platoVolado));
        response.put("salsas", salsas);

        return response;
    }

    private Map<String, Object> buildMenuService(String tipo, String descripcion, boolean incluyeCremas) {
        List<Map<String, Object>> categorias = new ArrayList<>();

        for (MenuCategory cat : MenuCategory.values()) {
            if (!incluyeCremas && cat == MenuCategory.CREMA) continue;

            List<MenuItem> items = menuItemService.findByCategoria(cat);
            Map<String, Object> categoria = new LinkedHashMap<>();
            categoria.put("nombre", cat.name());
            categoria.put("items", items.stream().map(m -> {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("id", m.getId());
                item.put("nombre", m.getNombre());
                if (m.getDescripcion() != null) item.put("descripcion", m.getDescripcion());
                return item;
            }).toList());
            categorias.add(categoria);
        }

        Map<String, Object> servicio = new LinkedHashMap<>();
        servicio.put("tipo", tipo);
        servicio.put("descripcion", descripcion);
        servicio.put("precioReferencia", "Consultar (varía según número de personas y requerimientos)");
        servicio.put("notas", List.of(
                "Requiere loza (costo adicional si no la provee el cliente)",
                "Incluye agua fresca (1 bitrolero por cada 60 personas)"
        ));
        servicio.put("categorias", categorias);

        return servicio;
    }
}
