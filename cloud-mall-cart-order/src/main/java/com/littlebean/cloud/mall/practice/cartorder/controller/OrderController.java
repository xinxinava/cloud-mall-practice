package com.littlebean.cloud.mall.practice.cartorder.controller;

import com.github.pagehelper.PageInfo;
import com.littlebean.cloud.mall.practice.cartorder.model.request.CreateOrderReq;
import com.littlebean.cloud.mall.practice.cartorder.model.vo.OrderVO;
import com.littlebean.cloud.mall.practice.cartorder.service.OrderService;
import com.littlebean.cloud.mall.practice.common.common.ApiRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 描述：     订单Controller
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("order/create")
    @ApiOperation("创建订单")
    public ApiRestResponse create(@RequestBody @Valid CreateOrderReq createOrderReq) {
        String orderNo = orderService.create(createOrderReq);
        return ApiRestResponse.success(orderNo);
    }

    @GetMapping("order/detail")
    @ApiOperation("订单详情")
    public ApiRestResponse detail(@RequestParam String orderNo) {
        OrderVO orderVO = orderService.detail(orderNo);
        return ApiRestResponse.success(orderVO);
    }

    @GetMapping("order/list")
    @ApiOperation("订单列表")
    public ApiRestResponse list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForCustomer(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("order/cancel")
    @ApiOperation("订单取消")
    public ApiRestResponse cancel(@RequestParam String orderNo) {
        orderService.cancel(orderNo);
        return ApiRestResponse.success();
    }

    @GetMapping("order/qrcode")
    @ApiOperation("生成支付二维码")
    public ApiRestResponse qrcode(@RequestParam String orderNo) {
        String pngAddress = orderService.qrcode(orderNo);
        return ApiRestResponse.success(pngAddress);
    }

    @GetMapping("pay")
    @ApiOperation("管理员订单列表")
    public ApiRestResponse pay(@RequestParam String orderNo){
        orderService.pay(orderNo);
        return ApiRestResponse.success();
    }
}
