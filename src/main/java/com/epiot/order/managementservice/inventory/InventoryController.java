package com.epiot.order.managementservice.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Inventory> createInventory(@PathVariable Long productId, @RequestParam Integer quantity) {
        try {
            Inventory createdInventory = inventoryService.createInventory(productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Or handle the exception differently
        }
    }


    @PutMapping("/product/{productId}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long productId, @RequestParam Integer quantity) {
        try {
            Inventory updatedInventory = inventoryService.updateInventory(productId, quantity);
            return ResponseEntity.ok(updatedInventory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long productId) {
        try {
            inventoryService.deleteInventory(productId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Or handle differently, e.g., return 404 if not found
        }
    }

    @PostMapping("/product/{productId}/add")
    public ResponseEntity<Void> addStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        try {
            inventoryService.addStock(productId, quantity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product/{productId}/remove")
    public ResponseEntity<Void> removeStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        try {
            inventoryService.removeStock(productId, quantity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Or handle differently based on the exception type
        }
    }
}