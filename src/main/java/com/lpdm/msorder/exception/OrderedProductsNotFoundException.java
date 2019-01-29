package com.lpdm.msorder.exception;

public class OrderedProductsNotFoundException extends RuntimeException {

    public OrderedProductsNotFoundException(){
        super("Ordered products not found");
    }
}
