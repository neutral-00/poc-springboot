package com.lousing.poc.aop2;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public void addProduct(String name) {
        System.out.println("📦 Adding product: " + name);
    }

    public void deleteProduct(int id) {
        System.out.println("🗑️ Deleting product with ID: " + id);
    }

    public String findProduct(int id) {
        return "Product-" + id;
    }
}
