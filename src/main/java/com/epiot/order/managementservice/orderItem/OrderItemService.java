package com.epiot.order.managementservice.orderItem;

import com.epiot.order.managementservice.order.Order;
import com.epiot.order.managementservice.order.OrderService;
import com.epiot.order.managementservice.product.Product;
import com.epiot.order.managementservice.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final OrderService orderService;


    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, ProductService productService, OrderService orderService) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.orderService = orderService;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem createOrderItem(Long orderId, Long productId, int quantity) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        OrderItem orderItem = new OrderItem(order, product, quantity, product.getPrice()); // Assuming Product has a price field
        return orderItemRepository.save(orderItem);
    }

    // other methods for updating and deleting order items

}
