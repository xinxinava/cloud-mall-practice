package com.littlebean.cloud.mall.practice.cartorder.model.dao;

import com.littlebean.cloud.mall.practice.cartorder.model.pojo.Cart;
import com.littlebean.cloud.mall.practice.cartorder.model.vo.CartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cartMapper")
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<CartVO> selectList(@Param("userId") Integer userId);

    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    Integer selectOrNot(@Param("userId") Integer userId, @Param("productId") Integer productId,
                        @Param("selected") Integer selected);
}