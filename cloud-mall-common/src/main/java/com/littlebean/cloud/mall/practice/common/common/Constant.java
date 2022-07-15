package com.littlebean.cloud.mall.practice.common.common;

import com.google.common.collect.Sets;
import com.littlebean.cloud.mall.practice.common.exception.MallException;
import com.littlebean.cloud.mall.practice.common.exception.MallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * 常量值
 */
@Component
public class Constant {

    public static final String SALT = "8svbsvjkweDF,.03[";

    public static final String MALL_USER = "mall_user";


    public interface ProductListOrderBy {

        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc", "price asc");
    }

    public interface SaleStatus{
        int NOT_SALE=0; //商品下架状态
        int SALE=1; //商品上架状态
    }

    public interface Cart{
        int UN_CHECKED=0; //商品没有被选中
        int CHECKED=1; //商品被选中
    }

    public enum OrderStatusEnum {
        CANCELED(0, "用户已取消"),
        NOT_PAID(10, "未付款"),
        PAID(20, "已付款"),
        DELIVERED(30, "已发货"),
        FINISHED(40, "交易完成");

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new MallException(MallExceptionEnum.NO_ENUM);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
