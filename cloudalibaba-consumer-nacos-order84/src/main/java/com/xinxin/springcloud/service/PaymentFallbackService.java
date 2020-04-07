package com.xinxin.springcloud.service;

import com.xinxin.springcloud.entities.CommonResult;
import com.xinxin.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444, "fallback");
    }
}
