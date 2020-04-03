package com.xinxin.springcloud.controller;

import com.xinxin.springcloud.entities.CommonResult;
import com.xinxin.springcloud.entities.Payment;
import com.xinxin.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    /**
     * 服务发现  获取当前注册中心的所有服务的信息
     */
    @Resource
    private DiscoveryClient discoveryClient;

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

    /**
     * 服务发现
     * @return
     */
    @GetMapping("/discovery")
    public Object discovery(){
        //获取注册中心现有的所有服务列表
        List<String> services = discoveryClient.getServices();
        for (String element:services){
            log.info("注册中心的服务:"+element);
        }

        //根据名字获取服务实例信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances){
            log.info("ServiceId==>{},Host==>{},Port==>{},uri==>{}",instance.getServiceId(),instance.getHost(),instance.getPort(),instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping("/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    /**
     * 链路跟踪
     *
     * @return
     */
    @GetMapping(value = "/zipkin")
    public String paymentZipkin() {
        return "hi,i'am paymentZipkin server fall back,welcome to atguigu,O(∩_∩)O哈哈~";
    }
}
