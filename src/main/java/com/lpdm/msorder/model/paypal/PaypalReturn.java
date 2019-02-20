package com.lpdm.msorder.model.paypal;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class PaypalReturn {

    private String paymentId;
    private String token;
    private String PayerID;

    public PaypalReturn() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayerID() {
        return PayerID;
    }

    public void setPayerID(String payerID) {
        PayerID = payerID;
    }

    @Override
    public String toString() {
        return "PaypalReturn{" +
                "paymentId='" + paymentId + '\'' +
                ", token='" + token + '\'' +
                ", PayerID='" + PayerID + '\'' +
                '}';
    }
}
