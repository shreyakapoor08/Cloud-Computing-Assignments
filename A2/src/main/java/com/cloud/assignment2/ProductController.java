package com.cloud.assignment2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/store-products")
    public ResponseEntity<ApiResponse> storeProducts(@RequestBody ProductRequest productObj) {

        for (Product product : productObj.products) {
            productService.saveProduct(product);
        }
        ApiResponse response = new ApiResponse("Success.");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/list-products")
    public ResponseEntity<ProductRequest> listProducts() {
        List<Product> productList = productService.getAllProducts();
        ProductRequest result = new ProductRequest();
        result.setProducts(productList);
        return ResponseEntity.ok(result);
    }

    static class ProductRequest{
        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

       private List<Product> products;
    }
}
