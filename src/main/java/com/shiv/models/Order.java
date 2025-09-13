package com.shiv.models;

import com.shiv.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Order {

    private int orderId;
    private List<Product> cartsItems;
    private double totalAmount;
    private OrderStatus status;//Here use Enum

    public Order() {
        this.cartsItems = new ArrayList<>();
        this.status = OrderStatus.PLACED;
    }

    public Order(int orderId, List<Product> cartsItems, double totalAmount) {
        this.cartsItems = cartsItems;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public List<Product> getCartsItems() {
        return cartsItems;
    }

    public void setCartsItems(List<Product> cartsItems) {
        this.cartsItems = cartsItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
