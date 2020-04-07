package com.xinxin.springcloud.alibaba.service;

import java.math.BigDecimal;

public interface AccountService {

    /**
     * 减库存
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    void decrease(Long userId, BigDecimal money);
}
