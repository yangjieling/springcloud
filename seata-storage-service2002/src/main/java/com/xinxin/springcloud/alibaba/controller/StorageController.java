package com.xinxin.springcloud.alibaba.controller;

import com.xinxin.springcloud.alibaba.domain.CommonResult;
import com.xinxin.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    /**
     * 减库存
     *
     * @param productId
     * @param count
     * @return
     */
    @PostMapping(value = "/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
        storageService.decrease(productId, count);
        return new CommonResult(200, "扣减库存成功");
    }
}
