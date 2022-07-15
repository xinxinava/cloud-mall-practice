package com.littlebean.cloud.mall.practice.user.service;


import com.littlebean.cloud.mall.practice.common.exception.MallException;
import com.littlebean.cloud.mall.practice.user.model.pojo.User;

public interface UserService {

    public void register(String userName, String password) throws MallException;

    User login(String userName, String password) throws MallException;

    void updateInformation(User user) throws MallException;

    boolean checkAdminRole(User user);
}
