package com.epiot.order.managementservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        return productRepository.findById(productId)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    // Update other fields as needed
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
    }
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
