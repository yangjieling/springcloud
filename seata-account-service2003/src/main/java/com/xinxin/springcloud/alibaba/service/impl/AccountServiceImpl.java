package com.xinxin.springcloud.alibaba.service.impl;

import com.xinxin.springcloud.alibaba.dao.AccountDao;
import com.xinxin.springcloud.alibaba.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("*******->account-service中扣减账户余额开始");
        // 模拟超时异常,全局事务回滚
        /*try {
            // 暂停20秒钟
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        accountDao.decrease(userId, money);
        log.info("*******->account-service中扣减账户余额结束");
    }
}
