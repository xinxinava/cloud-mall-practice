package com.littlebean.cloud.mall.practice.categoryproduct.service;


import com.github.pagehelper.PageInfo;
import com.littlebean.cloud.mall.practice.categoryproduct.model.pojo.Category;
import com.littlebean.cloud.mall.practice.categoryproduct.model.request.AddCategoryReq;
import com.littlebean.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
