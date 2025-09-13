package com.shiv.controller;

import com.shiv.models.Product;
import com.shiv.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity<Boolean> addProductInCart(
            @PathVariable int productId,
            @PathVariable int quantity
    ){
        boolean addProduct = cartService.addProductIntoCart(productId, quantity);
        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{cartId}/{productId}")
    public ResponseEntity<Boolean> removeProductFromCart(@PathVariable int cartId,@PathVariable int productId){
        boolean b = cartService.removeProductFromCart(cartId, productId);
        return new ResponseEntity<>(b,HttpStatus.OK);
    }

    @GetMapping("/view/{cartId}")
    public ResponseEntity<List<Product>> viewAllProduct(@PathVariable int cartId){
        List<Product> products = cartService.viewAllProducts(cartId);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/price/{cartId}")
    public ResponseEntity<Double> showPrice(@PathVariable int cartId){
        double finalAmount = cartService.priceWithDiscount(cartId);
        return new ResponseEntity<>(finalAmount,HttpStatus.OK);
    }
}
