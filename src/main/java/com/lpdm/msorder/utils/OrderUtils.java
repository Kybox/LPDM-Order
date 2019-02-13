package com.lpdm.msorder.utils;

import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderUtils {

    private static Logger log = LoggerFactory.getLogger(OrderUtils.class);

    /**
     * Get the total amount of taxes for an {@link Order}
     * @param order The {@link Order} object for the calculation
     * @return The amount of the taxes of the {@link Order}
     */
    public static double getTaxAmount(Order order){

        double totalTax = 0;

        for(OrderedProduct orderedProduct : order.getOrderedProducts()){

            Product product = orderedProduct.getProduct();

            double amount = product.getPrice();
            int quantity = orderedProduct.getQuantity();
            double subTotal = amount * quantity;
            double tax = product.getTax();

            totalTax += subTotal * (tax / 100);
        }

        return Math.round(totalTax * 100D) / 100D;
    }

    public static double getTotalAmount(Order order){

        double taxAmount = getTaxAmount(order);
        double totalAmount = 0;

        for(OrderedProduct orderedProduct : order.getOrderedProducts()){

            Product product = orderedProduct.getProduct();

            double price = product.getPrice();
            int quantity = orderedProduct.getQuantity();

            totalAmount += (price * quantity);
        }

        if(order.getDelivery() != null) {
            totalAmount += order.getDelivery().getAmount();
        }

        if(order.getCoupon() != null)
            totalAmount += order.getCoupon().getAmount();

        totalAmount += taxAmount;

        return Math.round(totalAmount * 100D) / 100D;
    }
}
