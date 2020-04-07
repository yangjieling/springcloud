package com.xinxin.springcloud.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageDao {

    /**
     * 减库存
     * @param productId
     * @param count
     * @return
     */
    public int decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
