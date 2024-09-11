package com.example.Product.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Product.model.Price;
import com.example.Product.model.Product;
import com.example.Product.service.PriceService;
import com.example.Product.service.ProductService;

import lombok.AllArgsConstructor;


@Controller     //RestController doesn't work
@AllArgsConstructor
public class SearchPageController {

    private final ProductService productService;
    private final PriceService priceService;

    @GetMapping("")
    public String search(){
        return "search-page";
    }

    @GetMapping("/search")
    public String searchResults(@RequestParam(required = false) String query, Model model){
        List<Product> searchResults = productService.searchProducts(query);
        model.addAttribute("productsList", searchResults);
        return "search-results";
    }

    @GetMapping("/product-details/{productId}")
    public String showProductDetails(@PathVariable Long productId, Model model) {
        // Retrieve product details from the database using productService
        Product product = productService.getProductById(productId);
        Price price = priceService.getPricesByProductId(productId.toString());

        // Pass the product details to the Thymeleaf template
        model.addAttribute("product", product);
        model.addAttribute("product_prices", price);

        return "product-details";
    }
}
