package com.shiv.controller;

import com.shiv.enums.OrderStatus;
import com.shiv.models.Order;
import com.shiv.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{cardId}")
    public ResponseEntity<Order> placeOrder(@PathVariable int cartId){
        Order order = orderService.placeOrder(cartId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }



    @GetMapping("/view/{orderId}")
    public ResponseEntity<Map<String, Object>> viewOrderDetails(@PathVariable int orderId){
        Map<String, Object> map = orderService.viewOrderDetails(orderId);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }



    @PutMapping("/status/{orderId}/{status}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable int orderId, @PathVariable OrderStatus status){
        orderService.updateOrderStatus(orderId,status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
