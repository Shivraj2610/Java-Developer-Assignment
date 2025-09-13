package com.shiv.models;

import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component
public class Cart {

    private int id;

    private Map<Product, Integer> productQuantities;

    public Cart() {
        this.productQuantities=new HashMap<>();
    }

    public Cart(int id) {
        this();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Product, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Product, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }
}
