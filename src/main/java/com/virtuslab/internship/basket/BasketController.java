package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Basket Controller", produces = "application/json")
@RestController
@RequestMapping(value = "/basket", produces = "application/json")
public class BasketController {

    private BasketService basketService;
    private ProductService productService;

    public BasketController(BasketService basketService, ProductService productService) {
        this.basketService = basketService;
        this.productService = productService;
    }

    @ApiOperation(value = "Get a list of products in the basket")
    @ApiResponses(value = {
            @ApiResponse(code=200,
                    message = "OK",
                    response = List.class,
                    examples = @Example(value = {@ExampleProperty(value = "[\n" +
                            "  {\n" +
                            "    \"name\": \"Bread\",\n" +
                            "    \"type\": \"GRAINS\",\n" +
                            "    \"price\": 5\n" +
                            "  },\n" +
                            "  {\n" +
                            "    \"name\": \"Bread\",\n" +
                            "    \"type\": \"GRAINS\",\n" +
                            "    \"price\": 5\n" +
                            "  }\n" +
                            "]"
                            , mediaType = "application/json")}))
    })
    @GetMapping
    public ResponseEntity<?> getProducts(){
        List<Product> list = new ArrayList<>();
        try {
            list = this.basketService.getProducts();
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Add a product to the basket")
    @ApiResponses(value = {
            @ApiResponse(code=200,
                    message = "OK",
                    response = List.class,
                    examples = @Example(value = {@ExampleProperty(value = "Product added successfully" , mediaType = "application/json")})),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Product with name \"bread\" not found", mediaType = "application/json")})),
    })
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestParam String name){
        try {
            Product product = this.productService.getProduct(name);
            this.basketService.addProduct(product);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Product added successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Remove a product from the basket")
    @ApiResponses(value = {
            @ApiResponse(code=200,
                    message = "OK",
                    response = List.class,
                    examples = @Example(value = {@ExampleProperty(value = "Product removed successfully" , mediaType = "application/json")})),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Product with name \"bread\" not found", mediaType = "application/json")})),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = String.class, examples = @Example(value = {@ExampleProperty(value = "Product wasn't found in the basket", mediaType = "application/json")})),
    })
    @DeleteMapping
    public ResponseEntity<?> removeProduct(@RequestParam String name){
        boolean value = true;

        try {
             value = this.basketService.removeProduct(productService.getProduct(name));
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!value)
            return new ResponseEntity<String>("Product wasn't found in the basket", HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<String>("Product removed successfully", HttpStatus.OK);
    }
}
