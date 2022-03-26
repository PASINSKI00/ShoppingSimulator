package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketRepository;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Receipt Controller", produces = "application/json")
@RestController
@RequestMapping(value = "/receipt", produces = "application/json")
public class ReceiptController {

    private final BasketRepository basketRepository;
    private final ReceiptGenerator receiptGenerator;

    public ReceiptController(BasketRepository basketRepository, ReceiptGenerator receiptGenerator) {
        this.basketRepository = basketRepository;
        this.receiptGenerator = receiptGenerator;
    }


    @ApiOperation(value = "Get a receipt for current basket")
    @ApiResponses(value = {
            @ApiResponse(code=200,
                    message = "OK",
                    response = List.class,
                    examples = @Example(value = {@ExampleProperty(value = "{\n" +
                            "  \"entries\": [\n" +
                            "    {\n" +
                            "      \"product\": {\n" +
                            "        \"name\": \"Cereals\",\n" +
                            "        \"type\": \"GRAINS\",\n" +
                            "        \"price\": 8\n" +
                            "      },\n" +
                            "      \"quantity\": 7,\n" +
                            "      \"totalPrice\": 56\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"product\": {\n" +
                            "        \"name\": \"Bread\",\n" +
                            "        \"type\": \"GRAINS\",\n" +
                            "        \"price\": 5\n" +
                            "      },\n" +
                            "      \"quantity\": 3,\n" +
                            "      \"totalPrice\": 15\n" +
                            "    }\n" +
                            "  ],\n" +
                            "  \"discounts\": [\n" +
                            "    \"FifteenPercentDiscount\",\n" +
                            "    \"TenPercentDiscount\"\n" +
                            "  ],\n" +
                            "  \"totalPrice\": 54.315\n" +
                            "}" , mediaType = "application/json")}))
    })
    @GetMapping()
    public ResponseEntity<?> getReceipt(){
        Receipt receipt;
        try {
            Basket basket = basketRepository.getBasket();
            receipt = receiptGenerator.generate(basket);
            receipt = FifteenPercentDiscount.apply(receipt);
            receipt = TenPercentDiscount.apply(receipt);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Receipt>(receipt, HttpStatus.OK);
    }
}
