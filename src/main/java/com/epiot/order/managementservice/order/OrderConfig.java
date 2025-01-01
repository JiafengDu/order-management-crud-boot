package com.epiot.order.managementservice.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OrderConfig {
    @Bean
    CommandLineRunner orderCommandLineRunner(OrderRepository repository) {
        return args -> {
            Order order1 = new Order(
                    123L,
                    199.99,
                    "PENDING",
                    "123 Main St, City, Country"
            );
            Order order2 = new Order(
                    456L,
                    299.99,
                    "COMPLETED",
                    "456 Oak Ave, Town, Country"
            );

            repository.saveAll(List.of(order1, order2));
        };
    }
}
