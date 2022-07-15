package com.littlebean.cloud.mall.practice.user.model.dao;

import com.littlebean.cloud.mall.practice.user.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component("userMapper")
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByName(String userName);

    User selectLogin(@Param("userName") String userName, @Param("password") String password);
}