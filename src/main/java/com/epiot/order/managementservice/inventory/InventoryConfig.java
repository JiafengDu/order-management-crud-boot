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
            ProductRepository productRepository) {
        return args -> {
            // You can retrieve products from the database to link inventory to
            // For example, assuming you have a product with name "Smart Thermostat":
            Product smartThermostat = productRepository.findByName("Smart Thermostat").orElse(null);
            Product smartLock = productRepository.findByName("Smart Lock").orElse(null);
            Product smartLighting = productRepository.findByName("Smart Lighting").orElse(null);


            if (smartThermostat != null && smartLock != null && smartLighting != null) {
                Inventory thermostatInventory = new Inventory(smartThermostat, 100);
                Inventory lockInventory = new Inventory(smartLock, 50);
                Inventory lightingInventory = new Inventory(smartLighting, 200);

                repository.saveAll(List.of(thermostatInventory, lockInventory, lightingInventory));
            } else {
                // Handle the case where the products aren't found (e.g., log a warning)
                throw new IllegalStateException("One or more products not found for inventory initialization.");
            }
        };
    }
}