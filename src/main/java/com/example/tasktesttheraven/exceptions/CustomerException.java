package com.example.tasktesttheraven.exceptions;

public class CustomerException extends IllegalArgumentException{
    public CustomerException(String message){
        super(message);
    }
}
