package com.xinxin.springcloud.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xinxin.springcloud.entities.CommonResult;
import com.xinxin.springcloud.entities.Payment;
import com.xinxin.springcloud.myhandler.CustomerBlockHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RateLimitController {

    @GetMapping(value = "/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名称限流测试");
    }

    public CommonResult handleException(BlockException exception){
        return new CommonResult(444,exception.getClass().getCanonicalName()+"\t服务不可用");
    }

    @GetMapping(value = "/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200,"by url限流测试OK", new Payment(2020L, IdUtil.simpleUUID()));
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException",
            fallbackClass = CustomerBlockHandler.class,fallback = "fallbackException")
    public CommonResult customerBlockHandler(){
//        int age = 10/0;
        return new CommonResult(200,"客户自定义 限流测试OK", new Payment(2020L, IdUtil.simpleUUID()));
    }
}