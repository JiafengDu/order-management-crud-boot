package com.epiot.order.managementservice.inventory;

import com.epiot.order.managementservice.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    // Constructor
    public Inventory(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Inventory() {

    }

    // Getters
    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Business logic methods
    public void addStock(Integer amount) {
        this.quantity += amount;
    }

    public void removeStock(Integer amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
        } else {
            throw new IllegalStateException("Not enough stock for product: " + this.product.getName());
        }
    }

    public boolean hasEnoughStock(Integer requestedAmount) {
        return this.quantity >= requestedAmount;
    }
}

