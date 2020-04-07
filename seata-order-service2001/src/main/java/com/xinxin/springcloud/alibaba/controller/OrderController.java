package com.xinxin.springcloud.alibaba.controller;

import com.xinxin.springcloud.alibaba.domain.CommonResult;
import com.xinxin.springcloud.alibaba.domain.Order;
import com.xinxin.springcloud.alibaba.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @GetMapping("order/create")
    public CommonResult create(Order order) {
        orderService.create(order);
        return new CommonResult(200, "订单创建成功");
    }
}