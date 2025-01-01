
package com.epiot.order.managementservice.orderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderItems") // Consider /api/orders/{orderId}/items for a more RESTful structure
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @PostMapping //  Consider /api/orders/{orderId}/items for creating order items associated with a specific order
    public ResponseEntity<OrderItem> createOrderItem(
            @RequestParam Long orderId,
            @RequestParam Long productId,
            @RequestParam int quantity) {

        try {
            OrderItem createdOrderItem = orderItemService.createOrderItem(orderId, productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Or handle more specifically
        }
    }

    // Add update and delete methods as needed
    // Example:
    /*
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem updatedOrderItem) {
        // ... implementation ...
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        // ... implementation ...
    }
    */
}
