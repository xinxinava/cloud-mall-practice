package com.littlebean.cloud.mall.practice.categoryproduct.service;

import com.github.pagehelper.PageInfo;
import com.littlebean.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.littlebean.cloud.mall.practice.categoryproduct.model.request.AddProductReq;
import com.littlebean.cloud.mall.practice.categoryproduct.model.request.ProductListReq;


public interface ProductService {
    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);

    void updateStock(Integer productId, Integer stock);
}
