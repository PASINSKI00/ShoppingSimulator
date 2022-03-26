package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();

        Map<Product,Integer> map = new HashMap<>();
        for(Product product : basket.getProducts()){
            if(!map.containsKey(product))
                map.put(product, 1);
            else
                map.put(product, map.get(product) + 1);
        }

        for(Product product : map.keySet()){
          receiptEntries.add(new ReceiptEntry(product, map.get(product)));
        }

        return new Receipt(receiptEntries);
    }
}
