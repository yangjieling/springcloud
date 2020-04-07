package com.xinxin.springcloud.handler;

import com.xinxin.springcloud.entities.CommonResult;

public class CustomerBlockHandler {

    public static CommonResult handlerException(Long id){
        return new CommonResult(444,"客户自定义，global handlerException---1");
    }

    public static CommonResult handlerException2(Long id){
        return new CommonResult(444,"客户自定义，global handlerException---2");
    }

    public static CommonResult fallbackException(Long id,Throwable e){
        return new CommonResult(444,"客户自定义，global fallbackException"+e.getMessage());
    }
}
