package com.example.Product.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Product.Exceptions.ExceptionsWithMessageConflict;
import com.example.Product.Exceptions.ExceptionsWithMessageNotFound;
import com.example.Product.model.Price;
import com.example.Product.service.PriceService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/{productId}")
    public ResponseEntity<Price> getPricesByProductId(@PathVariable String productId){
        return new ResponseEntity<>(priceService.getPricesByProductId(productId), OK);
    }

    @PostMapping
    public ResponseEntity<Void> savePrice(@RequestBody Price price) {
        priceService.savePrice(price);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping("/update-price")
    public ResponseEntity<Void> updatePrice(@RequestBody Price updatedPrice){
        priceService.updatePricesByProductId(updatedPrice);
        return new ResponseEntity<>(OK);
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
