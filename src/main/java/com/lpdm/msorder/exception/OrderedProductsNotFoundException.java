package com.lpdm.msorder.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class OrderedProductsNotFoundException extends RuntimeException {

    public OrderedProductsNotFoundException(){
        super("Ordered products not found");
    }
}
