package com.littlebean.cloud.mall.practice.user.service.impl;

import com.littlebean.cloud.mall.practice.common.exception.MallException;
import com.littlebean.cloud.mall.practice.common.exception.MallExceptionEnum;
import com.littlebean.cloud.mall.practice.common.util.MD5Utils;
import com.littlebean.cloud.mall.practice.user.model.dao.UserMapper;
import com.littlebean.cloud.mall.practice.user.model.pojo.User;
import com.littlebean.cloud.mall.practice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void register(String userName, String password) throws MallException {
        //查询用户名是否存在且不能重复
        User result = userMapper.selectByName(userName);
        if(result != null){
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }

        //写到数据库
        User user=new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        int count=userMapper.insertSelective(user);
        if(count == 0){
            throw new MallException(MallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public User login(String userName, String password) throws MallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if(user ==null){
            throw new MallException(MallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public void updateInformation(User user) throws MallException {
        int count=userMapper.updateByPrimaryKeySelective(user);
        if(count>1){
            throw new MallException(MallExceptionEnum.UPDATE_FAILD);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        // 1是普通用户，2是管理员
        return user.getRole().equals(2);
    }
}
