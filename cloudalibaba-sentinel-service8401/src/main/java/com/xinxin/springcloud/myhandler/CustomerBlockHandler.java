package com.xinxin.springcloud.myhandler;

import com.xinxin.springcloud.entities.CommonResult;

public class CustomerBlockHandler {

    public static CommonResult handlerException(){
        return new CommonResult(444,"客户自定义，global handlerException---1");
    }

    public static CommonResult handlerException2(){
        return new CommonResult(444,"客户自定义，global handlerException---2");
    }

    public static CommonResult fallbackException(){
        return new CommonResult(444,"客户自定义，global fallbackException");
    }
}
