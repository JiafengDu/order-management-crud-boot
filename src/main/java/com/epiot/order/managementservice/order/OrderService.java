package com.epiot.order.managementservice.order;

import com.epiot.order.managementservice.inventory.InventoryService;
import com.epiot.order.managementservice.orderItem.OrderItem;
import com.epiot.order.managementservice.product.ProductService;
import com.epiot.order.managementservice.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrder(Order order) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem item : order.getOrderItems()) {
            // Check if product exists and retrieve it (to get the price if needed)
            Product product = productService.getProductById(item.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + item.getProduct().getId()));

            // Use product price or item price (if provided)
            BigDecimal itemPrice = item.getPrice() != null ? item.getPrice() : product.getPrice();

            if (!inventoryService.hasEnoughStock(item.getProduct().getId(), item.getQuantity())) {
                throw new IllegalArgumentException("Not enough stock for product: " + item.getProduct().getName());
            }

            // Calculate total price
            totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(item.getQuantity())));

            // Reduce stock
            inventoryService.removeStock(item.getProduct().getId(), item.getQuantity());
        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    // Handle updates to order items and their impact on inventory (important!)
                    // ... (Logic for updating order items and inventory) ...

                    existingOrder.setOrderDate(updatedOrder.getOrderDate());
                    existingOrder.setUserId(updatedOrder.getUserId()); // Update other fields as needed
                    return orderRepository.save(existingOrder);
                })
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        // Restore inventory for each order item
        for (OrderItem item : order.getOrderItems()) {
            inventoryService.addStock(item.getProduct().getId(), item.getQuantity());
        }

        orderRepository.deleteById(id);
    }
}