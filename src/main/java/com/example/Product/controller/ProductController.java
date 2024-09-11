package com.example.Product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Product.Exceptions.ExceptionsWithMessageConflict;
import com.example.Product.Exceptions.ExceptionsWithMessageNotFound;
import com.example.Product.model.Product;
import com.example.Product.repo.ProductRepository;
import com.example.Product.service.PriceService;
import com.example.Product.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final PriceService priceService;

    @GetMapping("/get-all-products")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String ProductName) {
    List<Product> products = productService.getProducts(ProductName);
    return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/get-product-by-id")
    public ResponseEntity<Product> getProductById(@RequestParam Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        return productOptional.map(Product -> new ResponseEntity<>(Product, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create-product")
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        System.out.println("Received Product: " + newProduct.toString()); //debugging
        Product createdProduct = productService.createProduct(newProduct);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/update-product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product updatedProduct) {
        productService.updateProduct(updatedProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete-product-by-id")
    public ResponseEntity<Void> deleteProductById(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        priceService.deletePricesByProductId(productId.toString());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //#region Exception Handlers
    //Exception Handlers for both CONFLICT and NOT_FOUND statuses respectively
    @ExceptionHandler(ExceptionsWithMessageConflict.class)
    public ResponseEntity<String> handleExceptionsWithMessageConflict(ExceptionsWithMessageConflict exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExceptionsWithMessageNotFound.class)
    public ResponseEntity<String> handleExceptionsWithMessageNotFound(ExceptionsWithMessageNotFound exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    //#endregion
}
