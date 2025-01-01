package com.epiot.order.managementservice.inventory;

import com.epiot.order.managementservice.product.Product;
import com.epiot.order.managementservice.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductService productService;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, ProductService productService) {
        this.inventoryRepository = inventoryRepository;
        this.productService = productService;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public Inventory createInventory(Long productId, Integer quantity) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        Inventory inventory = new Inventory(product, quantity);
        return inventoryRepository.save(inventory);

    }


    @Transactional
    public Inventory updateInventory(Long productId, Integer quantity) {
        return inventoryRepository.findByProductId(productId)
                .map(existingInventory -> {
                    existingInventory.addStock(quantity);
                    return inventoryRepository.save(existingInventory);
                })
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product with ID: " + productId));
    }


    public void deleteInventory(Long productId) {
        inventoryRepository.deleteByProductId(productId);
    }


    @Transactional
    public void addStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product with ID: " + productId));
        inventory.addStock(quantity);
        inventoryRepository.save(inventory); // No need to explicitly save if using @Transactional
    }

    @Transactional
    public void removeStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product with ID: " + productId));
        inventory.removeStock(quantity);
        inventoryRepository.save(inventory); // No need to explicitly save if using @Transactional
    }

    public boolean hasEnoughStock(Long productId, Integer requestedQuantity) {
        return inventoryRepository.findByProductId(productId)
                .map(inventory -> inventory.hasEnoughStock(requestedQuantity))
                .orElse(false);
    }
}