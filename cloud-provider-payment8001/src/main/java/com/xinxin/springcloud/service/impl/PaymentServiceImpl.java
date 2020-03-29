package com.xinxin.springcloud.service.impl;

import com.xinxin.springcloud.dao.PaymentDao;
import com.xinxin.springcloud.entities.Payment;
import com.xinxin.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        int i = paymentDao.create(payment);
        log.info("插入数据的主键:{}",payment.getId());
        return i;
    }

    @Override
    public Payment getPaymentBuId(Long id) {
        Payment payment = paymentDao.getPaymentById(id);
        return payment;
    }
}
