package com.littlebean.cloud.mall.practice.cartorder.service.impl;

import com.littlebean.cloud.mall.practice.cartorder.Feign.ProductFeignClient;
import com.littlebean.cloud.mall.practice.cartorder.model.dao.CartMapper;
import com.littlebean.cloud.mall.practice.cartorder.model.pojo.Cart;
import com.littlebean.cloud.mall.practice.cartorder.model.vo.CartVO;
import com.littlebean.cloud.mall.practice.cartorder.service.CartService;
import com.littlebean.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.littlebean.cloud.mall.practice.common.common.Constant;
import com.littlebean.cloud.mall.practice.common.exception.MallException;
import com.littlebean.cloud.mall.practice.common.exception.MallExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId){
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO =  cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice()*cartVO.getQuantity());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count){
        vaildProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null){
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        }else {
            //商品已经在购物车中，商品数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    public void vaildProduct(Integer productId, Integer count){
        Product product = productFeignClient.detailForFeign(productId);

        //判断商品是否存在，商品是否上架
        if(product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)){
            throw new MallException(MallExceptionEnum.NOT_SALE);
        }
        //判断商品库存
        if(product.getStock()<count){
            throw new MallException(MallExceptionEnum.NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count){
        vaildProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null){
            //如果商品不在购物车，无法更新
            throw new MallException(MallExceptionEnum.UPDATE_FAILD);
        }else {
            //商品已经在购物车中，更新鼠标
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null){
            //如果商品不在购物车，无法更新
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }else {
            //商品已经在购物车中，更新鼠标
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected){
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if(cart == null){
            //如果商品不在购物车，无法更新
            throw new MallException(MallExceptionEnum.UPDATE_FAILD);
        }else {
            //商品已经在购物车中，更新鼠标
            cartMapper.selectOrNot(userId, productId, selected);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer selected){
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);
    }
}
