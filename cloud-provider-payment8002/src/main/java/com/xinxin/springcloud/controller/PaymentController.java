package com.xinxin.springcloud.controller;

import com.xinxin.springcloud.entities.CommonResult;
import com.xinxin.springcloud.entities.Payment;
import com.xinxin.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment){
        log.info("请求参数:{}",payment);
        int i = paymentService.create(payment);
        log.info("插入结果:{}",i);
        if (i > 0){
            return new CommonResult(200,"插入数据成功,serverPort: "+serverPort,i);
        }
        return new CommonResult(444,"插入数据失败");
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("请求参数:{}",id);
        Payment payment = paymentService.getPaymentBuId(id);
        log.info("查询结果:{}",payment);
        if (payment != null){
            return new CommonResult(200,"查询成功,serverPort: "+serverPort,payment);
        }
        return new CommonResult(4444,"没有对应记录，查询ID:"+id);
    }
}
