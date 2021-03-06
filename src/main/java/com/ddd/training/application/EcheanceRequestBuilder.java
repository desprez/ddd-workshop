package com.ddd.training.application;

import java.math.BigDecimal;
import java.util.Date;

public class EcheanceRequestBuilder {

    private EcheanceRequest echeanceRequest;

    public EcheanceRequestBuilder() {
        this.echeanceRequest = new EcheanceRequest();
    }

    public EcheanceRequest build() {
        return echeanceRequest;
    }

    public EcheanceRequestBuilder withPaymentDate(Date paymentDate) {
        echeanceRequest.setPaymentDate(paymentDate);
        return this;
    }

    public EcheanceRequestBuilder withCrd(BigDecimal crd) {
        echeanceRequest.setCrd(crd);
        return this;
    }
}
