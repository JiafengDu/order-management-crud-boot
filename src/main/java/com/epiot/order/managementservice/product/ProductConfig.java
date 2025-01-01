package com.epiot.order.managementservice.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    List<Product> initialProducts(ProductRepository productRepository) {
        Product smartThermostat = new Product(
                "Smart Thermostat",
                "A smart thermostat for controlling home temperature.",
                BigDecimal.valueOf(199.99)
        );

        Product smartLock = new Product(
                "Smart Lock",
                "A keyless smart lock for enhanced home security.",
                BigDecimal.valueOf(249.99)
        );

        Product smartLighting = new Product(
                "Smart Lighting",
                "Smart LED lights with adjustable color and brightness.",
                BigDecimal.valueOf(79.99)
        );

        return productRepository.saveAll(List.of(smartThermostat, smartLock, smartLighting));
    }
}