package com.example.proyectofinal.config;

import com.example.proyectofinal.entity.Guisado;
import com.example.proyectofinal.entity.MenuItem;
import com.example.proyectofinal.entity.Salsa;
import com.example.proyectofinal.enums.GuisadoCategory;
import com.example.proyectofinal.enums.MenuCategory;
import com.example.proyectofinal.repository.GuisadoRepository;
import com.example.proyectofinal.repository.MenuItemRepository;
import com.example.proyectofinal.repository.SalsaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GuisadoRepository guisadoRepository;
    private final MenuItemRepository menuItemRepository;
    private final SalsaRepository salsaRepository;

    public DataInitializer(GuisadoRepository guisadoRepository,
                           MenuItemRepository menuItemRepository,
                           SalsaRepository salsaRepository) {
        this.guisadoRepository = guisadoRepository;
        this.menuItemRepository = menuItemRepository;
        this.salsaRepository = salsaRepository;
    }

    @Override
    public void run(String... args) {
        if (guisadoRepository.count() > 0) return;

        initGuisados();
        initMenuItems();
        initSalsas();
    }

    private void initGuisados() {
        // POLLO
        guisadoRepository.save(new Guisado("En salsa guajillo", GuisadoCategory.POLLO));
        guisadoRepository.save(new Guisado("En crema con champiñones", GuisadoCategory.POLLO));
        guisadoRepository.save(new Guisado("En crema de elote con rajas", GuisadoCategory.POLLO));
        guisadoRepository.save(new Guisado("Fajita con pimiento y champiñones", GuisadoCategory.POLLO));
        guisadoRepository.save(new Guisado("En salsa verde con nopales", GuisadoCategory.POLLO));
        guisadoRepository.save(new Guisado("Con mole rojo o verde", GuisadoCategory.POLLO));
        guisadoRepository.save(new Guisado("En tinga", GuisadoCategory.POLLO));
        guisadoRepository.save(new Guisado("En adobo", GuisadoCategory.POLLO));
        guisadoRepository.save(new Guisado("Pechuga empanizada", GuisadoCategory.POLLO));

        // RES
        guisadoRepository.save(new Guisado("En pasilla con nopales", GuisadoCategory.RES));
        guisadoRepository.save(new Guisado("En salsa verde con nopales", GuisadoCategory.RES));
        guisadoRepository.save(new Guisado("En tres chiles con papas", GuisadoCategory.RES));
        guisadoRepository.save(new Guisado("A la mexicana", GuisadoCategory.RES));
        guisadoRepository.save(new Guisado("Alambre", GuisadoCategory.RES));

        // PUERCO
        guisadoRepository.save(new Guisado("En mole verde", GuisadoCategory.PUERCO));
        guisadoRepository.save(new Guisado("En morita con papas", GuisadoCategory.PUERCO));
        guisadoRepository.save(new Guisado("Con verdolagas en salsa verde", GuisadoCategory.PUERCO));
        guisadoRepository.save(new Guisado("En caldillo de jitomate con rajas", GuisadoCategory.PUERCO));
        guisadoRepository.save(new Guisado("Con calabazas, elote y jitomate", GuisadoCategory.PUERCO));
        guisadoRepository.save(new Guisado("Al pastor", GuisadoCategory.PUERCO));
        guisadoRepository.save(new Guisado("En adobo con papas", GuisadoCategory.PUERCO));
        guisadoRepository.save(new Guisado("En pipián con chilacayotes", GuisadoCategory.PUERCO));
        guisadoRepository.save(new Guisado("Cochinita pibil", GuisadoCategory.PUERCO));

        // VEGETARIANO
        guisadoRepository.save(new Guisado("Papas con rajas", GuisadoCategory.VEGETARIANO));
        guisadoRepository.save(new Guisado("Papas con chorizo", GuisadoCategory.VEGETARIANO));
        guisadoRepository.save(new Guisado("Rajas con crema", GuisadoCategory.VEGETARIANO));
        guisadoRepository.save(new Guisado("Chicharrón en salsa verde", GuisadoCategory.VEGETARIANO));
        guisadoRepository.save(new Guisado("Chicharrón en salsa de jitomate", GuisadoCategory.VEGETARIANO));
        guisadoRepository.save(new Guisado("Salchichas a la mexicana", GuisadoCategory.VEGETARIANO));
    }

    private void initMenuItems() {
        // CREMAS (solo para 3 tiempos)
        menuItemRepository.save(new MenuItem("Crema de elote", MenuCategory.CREMA));
        menuItemRepository.save(new MenuItem("Crema de poblano", MenuCategory.CREMA));
        menuItemRepository.save(new MenuItem("Crema de queso", MenuCategory.CREMA));
        menuItemRepository.save(new MenuItem("Crema de champiñón", MenuCategory.CREMA));
        menuItemRepository.save(new MenuItem("Crema de zanahoria", MenuCategory.CREMA));

        // PASTAS
        menuItemRepository.save(new MenuItem("Espagueti blanco", MenuCategory.PASTA));
        menuItemRepository.save(new MenuItem("Espagueti rojo", MenuCategory.PASTA));
        menuItemRepository.save(new MenuItem("Fusilli al chipotle", MenuCategory.PASTA));
        menuItemRepository.save(new MenuItem("Codo en crema con jamón", MenuCategory.PASTA));

        // PLATO FUERTE - POLLO
        menuItemRepository.save(new MenuItem("Pollo en crema de elote con rajas de poblano",
                MenuCategory.PLATO_FUERTE_POLLO));
        menuItemRepository.save(new MenuItem("Pollo enchilado",
                MenuCategory.PLATO_FUERTE_POLLO));
        menuItemRepository.save(new MenuItem("Pollo en mole rojo o verde",
                MenuCategory.PLATO_FUERTE_POLLO));
        menuItemRepository.save(new MenuItem("Rollo de pechuga con espinacas y queso manchego",
                MenuCategory.PLATO_FUERTE_POLLO,
                "Salsas disponibles: Crema de champiñón / Salsa de tres chiles / Salsa pasilla"));

        // PLATO FUERTE - CERDO
        menuItemRepository.save(new MenuItem("Lomo",
                MenuCategory.PLATO_FUERTE_CERDO,
                "Salsas: Ciruela, Enchilado, Tres chiles, A la naranja"));
        menuItemRepository.save(new MenuItem("Pierna",
                MenuCategory.PLATO_FUERTE_CERDO,
                "Salsas: Ciruela, Enchilado, Tres chiles, A la naranja"));
        menuItemRepository.save(new MenuItem("Costilla",
                MenuCategory.PLATO_FUERTE_CERDO,
                "Salsas: Ciruela, Enchilado, Tres chiles, A la naranja"));

        // ENSALADAS
        menuItemRepository.save(new MenuItem("Miami - lechuga italiana, arándanos, manzana, aderezo de yogurt",
                MenuCategory.ENSALADA));
        menuItemRepository.save(new MenuItem("Brasileña - zanahoria, crema y piña",
                MenuCategory.ENSALADA));
        menuItemRepository.save(new MenuItem("Waldos - manzana, crema y durazno",
                MenuCategory.ENSALADA));

        // GUARNICIONES
        menuItemRepository.save(new MenuItem("Verduras a la mantequilla (zanahoria, brócoli, ejotes)",
                MenuCategory.GUARNICION));
        menuItemRepository.save(new MenuItem("Papas gratinadas",
                MenuCategory.GUARNICION));
    }

    private void initSalsas() {
        salsaRepository.save(new Salsa("Crema de champiñón", "POLLO"));
        salsaRepository.save(new Salsa("Salsa de tres chiles", "POLLO"));
        salsaRepository.save(new Salsa("Salsa pasilla", "POLLO"));
        salsaRepository.save(new Salsa("Ciruela", "CERDO"));
        salsaRepository.save(new Salsa("Enchilado", "CERDO"));
        salsaRepository.save(new Salsa("Tres chiles", "CERDO"));
        salsaRepository.save(new Salsa("A la naranja", "CERDO"));
    }
}
