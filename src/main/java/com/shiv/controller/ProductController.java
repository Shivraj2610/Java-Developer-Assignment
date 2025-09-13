package com.shiv.controller;


import com.shiv.models.Product;
import com.shiv.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    //Add Product
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        //To understand flow
        logger.info("add Product Controller called");

        Product addedProduct = productService.addProduct(product);

        return new ResponseEntity<>(addedProduct,HttpStatus.CREATED);
    }

    //Update Product
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(int id, @RequestBody Product product){
        logger.info("Start Updating");

        Product updatedProduct = productService.updateProduct(id, product);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    //Get Product by Id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){

        Product productProductById = productService.getProductProductById(id);
        return new ResponseEntity<>(productProductById,HttpStatus.OK);
    }
}
