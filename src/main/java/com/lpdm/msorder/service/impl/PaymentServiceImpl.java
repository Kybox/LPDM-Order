package com.lpdm.msorder.service.impl;

import com.lpdm.msorder.exception.PaymentNotFoundException;
import com.lpdm.msorder.model.order.Order;
import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.lpdm.msorder.model.paypal.PaypalToken;
import com.lpdm.msorder.repository.PaymentRepository;
import com.lpdm.msorder.service.OrderService;
import com.lpdm.msorder.service.PaymentService;
import com.lpdm.msorder.service.PaypalService;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderService orderService;
    private final PaypalService paypalService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaypalService paypalService,
                              OrderService orderService,
                              PaymentRepository paymentRepository) {

        this.paypalService = paypalService;
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Process to the order payment
     * @param orderId The {@link Order} id to pay
     * @param redirectUrls The {@link RedirectUrls} for the redirection
     * @param id The client id for the Paypal API
     * @param secret The secret for the Paypal API
     * @return A {@link PaypalPayUrl} object with the header and the url redirection
     * @throws Exception Thrown if something goes wrong
     */
    @Override
    public PaypalPayUrl paypalPaymentProcess(int orderId,
                                             RedirectUrls redirectUrls,
                                             String id, String secret) throws Exception {

        Order order = orderService.findOrderById(orderId);

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

    /**
     * Find all payment methods
     * @return The {@link List} of {@link Payment} objects
     */
    @Override
    public List<Payment> findAllPayments() {

        return paymentRepository.findAll();
    }

    /**
     * Find a {@link Payment} object by its id
     * @param id The {@link Payment} id
     * @return The {@link Payment} object found
     */
    @Override
    public Payment findPaymentById(int id) {

        Optional<Payment> optPayment = paymentRepository.findById(id);
        if(!optPayment.isPresent()) throw new PaymentNotFoundException();

        return optPayment.get();
    }

    /**
     * Persist a new {@link Payment} object in the database
     * @param payment The {@link Payment} object to persist
     * @return The {@link Payment} object persisted
     */
    @Override
    public Payment savePayment(Payment payment) {

        return paymentRepository.save(payment);
    }

    /**
     * Delete a {@link Payment} object in the database
     * @param payment The {@link Payment} object to delete
     */
    @Override
    public void deletePayment(Payment payment) {

        paymentRepository.delete(payment);
    }

    /**
     * Check if a {@link Payment} object exist in the database
     * @param id The {@link Payment} id
     * @return True if the {@link Payment} exist, otherwise false
     */
    @Override
    public boolean checkIfPaymentExist(int id) {

        return paymentRepository.findById(id).isPresent();
    }
}
