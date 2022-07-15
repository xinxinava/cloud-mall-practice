package com.littlebean.cloud.mall.practice.categoryproduct.model.dao;

import com.littlebean.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.littlebean.cloud.mall.practice.categoryproduct.model.query.ProductListQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("productMapper")
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectByName(String name);

    int batchUpdateSellStatus(@Param("ids") Integer[] ids, @Param("sellStatue") Integer sellStatus);

    List<Product> selectListForAdmin();

    List<Product> selectList(@Param("query") ProductListQuery query);
}