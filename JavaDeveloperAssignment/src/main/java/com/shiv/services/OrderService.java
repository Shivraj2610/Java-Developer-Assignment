package com.shiv.services;

import com.shiv.enums.OrderStatus;
import com.shiv.models.Cart;
import com.shiv.models.Order;
import com.shiv.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;


    private final List<Order> recentOrders = new ArrayList<>();

    //To Place Order
    public Order placeOrder(int cartId) {
        try {
            //Get cart from CartService
            Cart cart = cartService.getCartById(cartId);

            //Check Cart is Empty
            if (cart.getProductQuantities().isEmpty()) {
                throw new RuntimeException("Cannot place order for empty cart");
            }

            //Get cart items
            List<Product> cartItems = new ArrayList<>(cart.getProductQuantities().keySet());

            //Calculate total amount from cart
            double totalAmount = cartService.priceWithDiscount(cartId);

            // 5. Generate unique order ID (using timestamp)
            int orderId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

            // 6. Create Order object
            Order order = new Order(orderId, cartItems, totalAmount);

            // 7. Reduce stock for each product in cart
            for (Map.Entry<Product, Integer> entry : cart.getProductQuantities().entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();

                // Reduce stock using ProductService
                boolean stockReduced = productService.reduceStock(product.getId(), quantity);
                if (!stockReduced) {
                    throw new RuntimeException("Failed to reduce stock for product: " + product.getName());
                }

                System.out.println("Stock reduced for " + product.getName() + " by " + quantity);
            }

            // 8. Clear cart after successful order
            cartService.clearCart(cartId);

            System.out.println("Order placed successfully!");
            System.out.println("Order ID: " + orderId);
            System.out.println("Total Amount: ₹" + totalAmount);
            System.out.println("Status: " + order.getStatus());

            return order;

        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
            throw new RuntimeException("Failed to place order: " + e.getMessage());
        }
    }




    //View Order Details
    public Map<String, Object> viewOrderDetails(int orderId) {
        try {
            // Find order in recent orders list
            Order order = recentOrders.stream()
                    .filter(o -> o.getOrderId() == orderId)
                    .findFirst()
                    .orElse(null);

            if (order == null) {
                throw new RuntimeException("Order not found with ID: " + orderId);
            }

            // Create detailed response
            Map<String, Object> orderDetails = new HashMap<>();

            // Basic order information
            orderDetails.put("orderId", order.getOrderId());
            orderDetails.put("status", order.getStatus());
            orderDetails.put("totalAmount", order.getTotalAmount());


            // Order items details
            List<Map<String, Object>> itemDetails = new ArrayList<>();
            for (Product product : order.getCartsItems()) {
                Map<String, Object> item = new HashMap<>();
                item.put("productId", product.getId());
                item.put("productName", product.getName());
                item.put("price", product.getPrice());
                itemDetails.add(item);
            }
            orderDetails.put("items", itemDetails);
            orderDetails.put("itemCount", order.getCartsItems().size());


            return orderDetails;

        } catch (Exception e) {
            System.out.println("Error viewing order details: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve order details: " + e.getMessage());
        }
    }


    //Update the Order Status
    public void updateOrderStatus(int orderId, OrderStatus status){
        Order order = recentOrders.stream()
                .filter(o -> o.getOrderId() == orderId)
                .findFirst()
                .orElseThrow(()->new RuntimeException("OrderId not match"));

        order.setStatus(status);

        System.out.println("Status is Updated");

    }
}
