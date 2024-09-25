package com.GBCE.service;

public class StockNotFound extends RuntimeException {

    public StockNotFound(String message) {
        super(message);
    }
}
