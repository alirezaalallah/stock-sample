package com.example.stocksample.exception;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException() {
        super("Stock not found");
    }
}
