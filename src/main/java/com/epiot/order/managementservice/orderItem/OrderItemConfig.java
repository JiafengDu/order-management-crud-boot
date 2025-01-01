package com.epiot.order.managementservice.orderItem;

import com.epiot.order.managementservice.order.Order;
import com.epiot.order.managementservice.order.OrderRepository;
import com.epiot.order.managementservice.product.Product;
import com.epiot.order.managementservice.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class OrderItemConfig {

    @Bean
    CommandLineRunner orderItemCommandLineRunner(
            OrderItemRepository orderItemRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository
    ) {
        return args -> {
            Order order1 = orderRepository.findById(123L).orElse(null);
            Product productA = productRepository.findByName("Smart Thermostat").orElse(null);
            Product productB = productRepository.findByName("Smart Lock").orElse(null);

            if (order1 != null && productA != null && productB != null) {
                OrderItem item1 = new OrderItem(order1, productA, 2, productA.getPrice().multiply(BigDecimal.valueOf(2)));
                OrderItem item2 = new OrderItem(order1, productB, 1, productB.getPrice());
                orderItemRepository.saveAll(List.of(item1, item2));
            } else {
                throw new IllegalStateException("Order or Product not found for OrderItem initialization.");
            }
        };
    }
}