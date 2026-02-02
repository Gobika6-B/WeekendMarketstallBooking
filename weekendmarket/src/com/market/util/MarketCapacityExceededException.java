package com.market.util;

public class MarketCapacityExceededException extends Exception {

    private static final long serialVersionUID = 1L;

    public MarketCapacityExceededException(String message) {
        super(message);
    }
}
