package com.littlebean.cloud.mall.practice.user.controller;

import com.littlebean.cloud.mall.practice.common.common.ApiRestResponse;
import com.littlebean.cloud.mall.practice.common.common.Constant;
import com.littlebean.cloud.mall.practice.common.exception.MallException;
import com.littlebean.cloud.mall.practice.common.exception.MallExceptionEnum;
import com.littlebean.cloud.mall.practice.user.model.pojo.User;
import com.littlebean.cloud.mall.practice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName,
                                    @RequestParam("password") String password) throws MallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }

        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }

        //密码长度不能小于八位
        if(password.length()<8){
            return ApiRestResponse.error(MallExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(userName, password);

        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws MallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }

        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);

        //保存用户信息时不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.MALL_USER, user);
        return ApiRestResponse.success(user);
    }

    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) throws MallException {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if(currentUser == null){
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        User user=new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
    }

    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws MallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }

        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);

        //校验管理员
        if (userService.checkAdminRole(user)) {
            //保存用户信息时不保存密码
            user.setPassword(null);
            session.setAttribute(Constant.MALL_USER, user);
            return ApiRestResponse.success(user);
        }else {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
        }
    }

    @PostMapping("checkAdminRole")
    @ResponseBody
    public Boolean checkAdminRole(@RequestBody User user){
        return userService.checkAdminRole(user);
    }

    /**
     * 获取当前登录对象
     * @param session
     * @return
     */
    @GetMapping("/GetUser")
    @ResponseBody
    public User getUser(HttpSession session){
        User user= (User) session.getAttribute(Constant.MALL_USER);
        return user;
    }

}
