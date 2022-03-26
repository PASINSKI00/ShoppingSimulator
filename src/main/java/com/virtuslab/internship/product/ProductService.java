package com.virtuslab.internship.product;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductDb productDb;

    public ProductService(ProductDb productDb) {
        this.productDb = productDb;
    }

    public Product getProduct(String name){
        return productDb.getProduct(name);
    }
}
