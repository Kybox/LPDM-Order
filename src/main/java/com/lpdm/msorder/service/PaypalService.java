package com.lpdm.msorder.service;

import com.lpdm.msorder.model.paypal.PaypalConfig;
import com.lpdm.msorder.model.paypal.PaypalReturn;
import com.lpdm.msorder.model.paypal.PaypalToken;

import java.io.IOException;

public interface PaypalService {

    String paypalPayment(String clientId, String secret);

    PaypalToken generatePaypalToken(String cliendId, String secret) throws IOException;

    PaypalConfig getPaypalConfig(PaypalToken paypalToken);

    void getTransactionDetails(PaypalReturn paypalReturn, String cliendId, String secret);
}
