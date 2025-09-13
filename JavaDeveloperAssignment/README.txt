To Run This Project Follow bellow instructions

1. This is Spring Boot Project you just open it in IntelliJ idea or any other
2. and Run the Application's main file JavaDeveloperAssignmentApplication.java
3. and the application run on http://localhost:8080 this url


//How to Check the REST API

To check REST apis use PostMan

And check following features by using there url I give Url and Feature name bellow


	Features Name			URL 								HTTP Method

	1. Add Product 			http://localhost:8080/product/add				POST
	
	2. Update Product		http://localhost:8080/update/{productId}			PUT

	3. Get Product by Id		http://localhost:8080/{productId}				GET

	4. Add Product in Cart		http://localhost:8080/cart/add/{productId}/{quantity}		POST

	5. Remove From Cart		http://localhost:8080/cart/view/{cartId}			DELETE

	6. View All Product		http://localhost:8080/cart/view/{cartId}			GET

	7. Show Price after Discount	http://localhost:8080/cart/price/{cartId}			GET

	8. Place Order			http://localhost:8080/orders/place/{cartId}			POST

	9. View Order Details		http://localhost:8080/orders/view/{orderId}			GET

	10. Update Order Status		http://localhost:8080/order/status/{orderId/{status}		PUT