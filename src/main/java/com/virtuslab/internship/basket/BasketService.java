package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    private BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public List<Product> getProducts() {
        return basketRepository.getProducts();
    }

    public void addProduct(Product product) {
        basketRepository.addProduct(product);
    }

    public boolean removeProduct(Product product) {
        return basketRepository.removeProduct(product);
    }
}
