package com.lpdm.msorder.model.paypal;

import java.util.Map;

public class PaypalPayUrl {

    private String redirectUrl;
    private Map<String, String> headers;

    public PaypalPayUrl() {
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "PaypalPayUrl{" +
                "redirectUrl='" + redirectUrl + '\'' +
                ", headers=" + headers +
                '}';
    }
}
