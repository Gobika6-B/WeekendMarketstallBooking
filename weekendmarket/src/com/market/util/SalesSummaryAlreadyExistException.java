package com.market.util;

public class SalesSummaryAlreadyExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public SalesSummaryAlreadyExistException(String message) {
        super(message);
    }
}
