package com.xinxin.springcloud.service;

import com.xinxin.springcloud.entities.Payment;

public interface PaymentService {

    public int create(Payment payment);

    public Payment getPaymentBuId(Long id);
}
