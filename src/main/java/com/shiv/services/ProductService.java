package com.shiv.services;

import com.shiv.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private int id=0;
    private List<Product> productList =new ArrayList<>();

    //This method to Add the New Product
    public Product addProduct(Product product){
        //Add the Product into productList
        product.setId(++id);
        productList.add(product);
        return product;
    }


    //This method to Update the Product stock
    public Product updateProduct(int id, Product productForUpdate){

        //This is the Logic to Update the Existing Product
        return productList.stream().filter(p->id==p.getId()).findFirst()
                .map(
                        p ->{
                            p.setName(productForUpdate.getName());
                            p.setPrice(productForUpdate.getPrice());
                            p.setStockQuantity(productForUpdate.getStockQuantity());
                            return p;
                        }
                ).orElseThrow(() -> new RuntimeException("Product is not Found with this Id"));
    }


    //Get Product By Id
    public Product getProductProductById(int id){

        Product product = productList.stream().filter(p -> id == p.getId()).findAny().get();
        return product;
    }


    //Reduce Stock
    boolean isReduced=false;
    public boolean reduceStock(int id, int quantity){

        productList.forEach(
                p ->
                {
                    //Reduce the Count of Stock
                    if(id==p.getId()){
                        p.setStockQuantity(p.getStockQuantity()-quantity);
                        isReduced=true;
                    }
                }
        );
        return isReduced;
    }
}
