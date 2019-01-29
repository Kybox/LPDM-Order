package com.lpdm.msorder.service;

import com.lpdm.msorder.exception.AddressNotFoundException;
import com.lpdm.msorder.exception.OrderedProductsNotFoundException;
import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.model.user.User;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.PayPalRESTException;

import java.io.IOException;
import java.util.List;

public interface PaypalService {

    /**
     * Generate the {@link Item} objects from the {@link OrderedProduct}
     * @param orderedProductList The list of {@link OrderedProduct}
     * @return A list of {@link Item} objects
     * @throws OrderedProductsNotFoundException Thrown if the {@link List<OrderedProduct>} is empty
     */
    List<Item> generateOrderedProducts(List<OrderedProduct> orderedProductList) throws OrderedProductsNotFoundException;

    /**
     * Generate the {@link ShippingAddress} object from the {@link User} data
     * @param user The {@link User} who made the order
     * @return The {@link ShippingAddress} object generated
     * @throws AddressNotFoundException Thrown if the {@link User} address object is not found
     */
    ShippingAddress generateShippingAddress(User user) throws AddressNotFoundException;

    ItemList generateItemList(List<Item> items, String phoneNumber, ShippingAddress shippingAddress);
    String paymentProcess(int orderId, ItemList itemList, RedirectUrls redirectUrls, String id, String secret);
    PaypalToken generatePaypalToken() throws IOException;


    String getTransactionDetails(PaypalReturn paypalReturn, String cliendId, String secret) throws PayPalRESTException;
}
