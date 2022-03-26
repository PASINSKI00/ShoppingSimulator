package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FifteenPercentDiscountTest {

    @Test
    void shouldApply15PercentDiscountWhenBasketContainsAtLeast3Grains() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");

        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);

        var expectedTotalPrice = bread.price().add(cereals.price()).add(bread.price()).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = FifteenPercentDiscount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountWhenBasketDoesntContainAtLeast3Grains() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");

        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);

        var expectedTotalPrice = bread.price().add(cereals.price());

        // When
        var receiptAfterDiscount = FifteenPercentDiscount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountWhenIsAlreadyApplied() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");

        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);

        // When
        var receiptAfterDiscount = FifteenPercentDiscount.apply(receipt);
        var receiptAfterSecondDiscount = FifteenPercentDiscount.apply(receiptAfterDiscount);

        // Then
        assertEquals(1, receiptAfterSecondDiscount.discounts().size());
    }
}