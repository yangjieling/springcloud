package com.xinxin.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xinxin.springcloud.entities.CommonResult;
import com.xinxin.springcloud.entities.Payment;
import com.xinxin.springcloud.handler.CustomerBlockHandler;
import com.xinxin.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@RestController
public class CircleBreakerController {

    private static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback")
//    @SentinelResource(value = "fallback",fallback = "handlerFallback")
    @SentinelResource(value = "fallback",
            blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException",
            fallbackClass = CustomerBlockHandler.class,fallback = "fallbackException")
//    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler")
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult<Payment> commonResult = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常");
        } else if (id == 5){
            int age = 10/0;
        }else if (commonResult.getData() == null) {
            throw new NullPointerException("NullPointerException,该ID没有记录，空指针异常");
        }
        return commonResult;
    }

    // 本例是fallback
    public CommonResult handlerFallback(Long id, Throwable e) {
        Payment payment = new Payment(id, null);
        return new CommonResult(444, "兜底异常handler，exception内容" + e.getMessage(), payment);
    }

    public CommonResult blockHandler(Long id, BlockException exception){
        Payment payment = new Payment(id, null);
        return new CommonResult<>(445, "blockHandler-sentinel 限流，无此流水号：blockException" + exception.getMessage(), payment);
    }

    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }

}
