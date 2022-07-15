package com.littlebean.cloud.mall.practice.cartorder.model.dao;


import com.littlebean.cloud.mall.practice.cartorder.model.pojo.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("orderItemMapper")
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> selectByOrderNo(String orderNo);
}