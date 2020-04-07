package com.xinxin.springcloud.alibaba.dao;

import com.xinxin.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    public int create(Order order);

    public int  update(@Param("userId") Long userId,@Param("status") Integer status);
}