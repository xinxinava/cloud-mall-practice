package com.littlebean.cloud.mall.practice.cartorder.model.dao;


import com.littlebean.cloud.mall.practice.cartorder.model.pojo.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("orderMapper")
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderNo(String orderNo);

    List<Order> selectForCustomer(Integer userId);
    List<Order> selectAllForAdmin();
}