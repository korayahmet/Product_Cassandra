package com.example.Product.Exceptions;

public class ExceptionsWithMessageNotFound extends RuntimeException{
    public ExceptionsWithMessageNotFound(String message){
        super(message);
    }
}
