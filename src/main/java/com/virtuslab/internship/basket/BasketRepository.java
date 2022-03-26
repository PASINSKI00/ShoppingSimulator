package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BasketRepository {

    private Basket basket;

    public BasketRepository() {
        this.basket = new Basket();
    }

    public List<Product> getProducts() {
        return basket.getProducts();
    }

    public void addProduct(Product product) {
        this.basket.addProduct(product);
    }

    public boolean removeProduct(Product product) {
        return this.basket.removeProduct(product);
    }
}
