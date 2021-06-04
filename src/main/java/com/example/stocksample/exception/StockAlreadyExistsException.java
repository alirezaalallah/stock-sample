package com.example.stocksample.exception;

public class StockAlreadyExistsException extends RuntimeException {
    public StockAlreadyExistsException() {
        super("Stock is already exists");
    }
}
