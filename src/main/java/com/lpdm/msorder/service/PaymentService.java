package com.lpdm.msorder.service;

import com.lpdm.msorder.model.order.Payment;
import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.paypal.api.payments.RedirectUrls;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PaymentService {

    PaypalPayUrl paypalPaymentProcess(int OrderId,
                                      RedirectUrls redirectUrls,
                                      String id, String secret) throws Exception;

    List<Payment> findAllPayments();
    Payment findPaymentById(int id);
    Payment savePayment(Payment payment);
    void deletePayment(Payment payment);
    boolean checkIfPaymentExist(int id);
}
