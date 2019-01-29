package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.OrderedProduct;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.model.user.User;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;

import java.io.IOException;
import java.util.List;

public interface PaypalService {

    List<Item> generateOrderedProducts(List<OrderedProduct> orderedProductList);
    ShippingAddress generateShippingAddress(User user);
    ItemList generateItemList(List<Item> items, String phoneNumber, ShippingAddress shippingAddress);
    String paymentProcess(int orderId, ItemList itemList, RedirectUrls redirectUrls, String id, String secret);
    PaypalToken generatePaypalToken() throws IOException;

    /*
    String paypalPayment();
    */

    void getTransactionDetails(PaypalReturn paypalReturn, String cliendId, String secret);
}
