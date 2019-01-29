package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.controller.json.FormatJson;
import com.lpdm.msorder.exception.OrderNotFoundException;
import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.PaymentService;
import com.lpdm.msorder.service.PaypalService;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderService orderService;
    private final PaypalService paypalService;

    @Autowired
    private FormatJson formatJson;

    @Autowired
    public PaymentServiceImpl(PaypalService paypalService,
                              OrderService orderService) {

        this.paypalService = paypalService;
        this.orderService = orderService;
    }

    @Override
    public PaypalPayUrl paypalPaymentProcess(int orderId,
                                             RedirectUrls redirectUrls,
                                             String id, String secret) throws IOException {

        Optional<Order> optOrder = orderService.findOrderById(orderId);
        if(!optOrder.isPresent()) throw new OrderNotFoundException();
        Order order = optOrder.get();
        order = formatJson.formatOrder(order);

        PaypalToken paypalToken = paypalService.generatePaypalToken();

        List<Item> items = paypalService.generateOrderedProducts(order.getOrderedProducts());
        ShippingAddress shippingAddress = paypalService.generateShippingAddress(order.getCustomer());
        ItemList itemList = paypalService.generateItemList(items, order.getCustomer().getTel(), shippingAddress);

        String redirectUrl = paypalService.paymentProcess(orderId, itemList, redirectUrls, id, secret);

        Map<String, String> mapHeaders = new HashMap<>();
        mapHeaders.put(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        mapHeaders.put(HttpHeaders.AUTHORIZATION, "Bearer " + paypalToken.getAccess_token());

        PaypalPayUrl paypalPayUrl = new PaypalPayUrl();
        paypalPayUrl.setRedirectUrl(redirectUrl);
        paypalPayUrl.setHeaders(mapHeaders);

        return paypalPayUrl;
    }
}
