package com.epiot.order.managementservice.order;

import com.epiot.order.managementservice.orderItem.OrderItem;
import com.epiot.order.managementservice.product.Product;
import com.epiot.order.managementservice.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class OrderConfig {
        @Bean
        CommandLineRunner orderCommandLineRunner(OrderRepository orderRepository, ProductRepository productRepository) {
            return args -> {
                Product product1 = productRepository.findById(1L).orElse(null);
                Product product2 = productRepository.findById(2L).orElse(null);

                if (product1 == null || product2 == null) {
                    System.err.println("Products not found.  Order initialization failed.");
                    return;
                }

                OrderItem orderItem1 = new OrderItem(null, product1, 2, product1.getPrice());
                OrderItem orderItem2 = new OrderItem(null, product2, 1, product2.getPrice());

                Order order1 = new Order(123L);
                order1.addOrderItem(orderItem1);

                Order order2 = new Order(456L);
                order2.addOrderItem(orderItem2);

                orderRepository.saveAll(List.of(order1, order2));
            };
        }
}