package com.lpdm.msorder.model.paypal;

public class PaypalConfig {

    private String authToken;
    private String postUrl;
    private String business;
    private String returnUrl;

    public PaypalConfig() {
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString() {
        return "PaypalConfig{" +
                "authToken='" + authToken + '\'' +
                ", postUrl='" + postUrl + '\'' +
                ", business='" + business + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                '}';
    }
}
