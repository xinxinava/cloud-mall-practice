package com.littlebean.cloud.mall.practice.zuul.feign;

import com.littlebean.cloud.mall.practice.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cloud-mall-user")
public interface UserFeignClient {

    @PostMapping("/checkAdminRole")
    public Boolean checkAdminRole(@RequestBody User user);
}
