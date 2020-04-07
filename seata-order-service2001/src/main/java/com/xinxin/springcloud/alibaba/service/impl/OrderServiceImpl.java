package com.xinxin.springcloud.alibaba.service.impl;

import com.xinxin.springcloud.alibaba.dao.OrderDao;
import com.xinxin.springcloud.alibaba.domain.Order;
import com.xinxin.springcloud.alibaba.service.AccountService;
import com.xinxin.springcloud.alibaba.service.OrderService;
import com.xinxin.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单说:
     * 下订单->减库存->减余额->改状态
     * GlobalTransactional seata开启分布式事务,异常时回滚,name保证唯一即可
     *
     * @param order 订单对象
     */
    @Override
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("======>新建订单开始");
        orderDao.create(order);
        log.info("======>新建订单结束");
        log.info("======>订单微服务开始调用库存,做扣减Count");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("======>订单微服务开始调用库存,做扣减End");
        log.info("======>订单微服务开始调用账户,做扣减Money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("======>订单微服务开始调用账户,做扣减End");
        log.info("======>修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("======>下订单结束了,O(∩_∩)O哈哈~");
    }
}