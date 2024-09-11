package com.example.Product.Exceptions;

public class ExceptionsWithMessageConflict extends RuntimeException{
    public ExceptionsWithMessageConflict(String message){
        super(message);
    }
}
