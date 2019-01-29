package com.lpdm.msorder.service;

import com.lpdm.msorder.model.paypal.PaypalPayUrl;
import com.paypal.api.payments.RedirectUrls;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PaymentService {

    PaypalPayUrl paypalPaymentProcess(int OrderId,
                                      RedirectUrls redirectUrls) throws IOException;
}
