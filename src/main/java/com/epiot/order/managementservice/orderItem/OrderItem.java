package com.epiot.order.managementservice.orderItem;

import com.epiot.order.managementservice.order.Order;
import com.epiot.order.managementservice.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    // order item come from product list,
    // adding orderItems will minus the inventory number of this product
    // each order item is associated with an order, an order can have many order items
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private BigDecimal price;


    // Constructors
    public OrderItem() {}

    public OrderItem(Order order, Product product, int quantity, BigDecimal price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // For testing purposes with OrderItemConfig
    public OrderItem(Long orderId, String productName, int quantity, double price) {
        this.order = new Order();
        this.order.setId(orderId);
        this.product = new Product();
        this.product.setName(productName);
        this.quantity = quantity;
        this.price = BigDecimal.valueOf(price);
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}