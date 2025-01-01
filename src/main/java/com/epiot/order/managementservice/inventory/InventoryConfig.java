package com.epiot.order.managementservice.inventory;

import com.epiot.order.managementservice.product.Product;
import com.epiot.order.managementservice.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InventoryConfig {

    @Bean
    CommandLineRunner inventoryCommandLineRunner(
            InventoryRepository repository,
            List<Product> initialProducts) { // Inject the list of products
        return args -> {
            Product smartThermostat = initialProducts.stream().filter(p -> p.getName().equals("Smart Thermostat")).findFirst().orElse(null);
            Product smartLock = initialProducts.stream().filter(p -> p.getName().equals("Smart Lock")).findFirst().orElse(null);
            Product smartLighting = initialProducts.stream().filter(p -> p.getName().equals("Smart Lighting")).findFirst().orElse(null);

            if (smartThermostat != null && smartLock != null && smartLighting != null) {
                Inventory thermostatInventory = new Inventory(smartThermostat, 100);
                Inventory lockInventory = new Inventory(smartLock, 50);
                Inventory lightingInventory = new Inventory(smartLighting, 200);

                repository.saveAll(List.of(thermostatInventory, lockInventory, lightingInventory));
            } else {
                throw new IllegalStateException("One or more products not found for inventory initialization.");
            }
        };
    }
}
