package com.shiv.services;

import com.shiv.models.Cart;
import com.shiv.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private ProductService productService;

    private int id = 100;

    private final Map<Integer, Cart> cartStorage = new HashMap<>();

    // Add Product into Cart
    public boolean addProductIntoCart(int cartId, int productId, int quantity) {

        // Check the quantity is realistic or not
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0");
            return false;
        }

        try {
            Cart cart;
            if(cartStorage.isEmpty()){
                System.out.println("Cart Created");
                cart = new Cart(cartId);
                cartStorage.put(cartId,cart);
            }else{
                cart = getCartById(cartId);
            }


            // Get product from ProductService
            Product product = productService.getProductProductById(productId);

            // Get current quantity of this product in cart (0 if not present)
            int currentQuantityInCart = cart.getProductQuantities().getOrDefault(product, 0);

            // Calculate new total quantity
            int newTotalQuantity = currentQuantityInCart + quantity;

            // Check stock availability
            if (product.getStockQuantity() < newTotalQuantity) {
                System.out.println("Cannot add " + quantity + " items of '" + product.getName() +
                        "'. Available stock: " + product.getStockQuantity() +
                        ", Already in cart: " + currentQuantityInCart +
                        ", Total requested: " + newTotalQuantity);
                return false;
            }

            // Add product in cart by using Map.put()
            cart.getProductQuantities().put(product, newTotalQuantity);

            System.out.println("Successfully added " + quantity + " x " + product.getName() + " to cart");
            System.out.println("   Total quantity in cart: " + newTotalQuantity);

            return true;

        } catch (Exception e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
            return false;
        }
    }

    // Remove the Product from Cart
    public boolean removeProductFromCart(int cartId, int productId) {
        try {

            // Get cart from storage
            Cart cart = getCartById(cartId);

            // Get product from ProductService
            Product product = productService.getProductProductById(productId);

            // Check if product exists in cart
            if (!cart.getProductQuantities().containsKey(product)) {
                System.out.println("Product '" + product.getName() + "' not found in cart");
                return false;
            }

            // Remove product from cart using Map.remove()
            Integer removedQuantity = cart.getProductQuantities().remove(product);

            System.out.println("Successfully removed " + removedQuantity + " x " + product.getName() + " from cart");

            return true;

        } catch (Exception e) {
            System.out.println("Error removing product from cart: " + e.getMessage());
            return false;
        }
    }

    // View The All Product from Cart
    public List<Product> viewAllProducts(int cartId) {

        Cart cart = getCartById(cartId);
        Map<Product, Integer> productQuantities = cart.getProductQuantities();
        List<Product> productList = new ArrayList<>(productQuantities.keySet());

        return productList;
    }

    // Get the Final amount with 10% discount
    private double totalPrice = 0;

    public double priceWithDiscount(int cartId) {
        Cart cart = getCartById(cartId);
        Map<Product, Integer> productQuantities = cart.getProductQuantities();
        List<Product> productList = new ArrayList<>(productQuantities.keySet());

        productList.forEach(
                p -> {
                    totalPrice += p.getPrice();
                });

        // Price with 10% Discount
        double finalAmount = totalPrice - (totalPrice / 10);

        return totalPrice;
    }

    // Clear the Cart
    public void clearCart(int cartId) {
        Cart cart = getCartById(cartId);
        cart.getProductQuantities().clear();
    }

    // Some Helper methods
    public Cart getCartById(int cartId) {
        Cart cart = cartStorage.get(cartId);
        if (cart == null) {
            return null;
        }
        return cart;
    }

    public Cart createCart(int cartId) {
        Cart cart = new Cart(cartId);
        cartStorage.put(cartId, cart);
        return cart;
    }

    public Map<Integer, Cart> getAllCarts() {
        return cartStorage;
    }
}
