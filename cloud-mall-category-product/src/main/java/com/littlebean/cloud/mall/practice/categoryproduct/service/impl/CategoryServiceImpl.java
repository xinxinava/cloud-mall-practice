package com.littlebean.cloud.mall.practice.categoryproduct.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebean.cloud.mall.practice.categoryproduct.model.dao.CategoryMapper;
import com.littlebean.cloud.mall.practice.categoryproduct.model.pojo.Category;
import com.littlebean.cloud.mall.practice.categoryproduct.model.request.AddCategoryReq;
import com.littlebean.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;
import com.littlebean.cloud.mall.practice.categoryproduct.service.CategoryService;
import com.littlebean.cloud.mall.practice.common.exception.MallException;
import com.littlebean.cloud.mall.practice.common.exception.MallExceptionEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if(categoryOld != null){
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if(count == 0){
            throw new MallException(MallExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Category updateCategory) {
        if(updateCategory.getName()!=null){
            Category categoryOld=categoryMapper.selectByName(updateCategory.getName());
            if(categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())){
                throw new MallException(MallExceptionEnum.NAME_EXISTED);
            }
        }
        int count=categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if(count==0){
            throw new MallException(MallExceptionEnum.UPDATE_FAILD);
        }
    }

    @Override
    public void delete(Integer id){
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        if(categoryOld == null){
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(categoryOld.getId());
        if(count == 0){
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize, "type, order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo=new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList, parentId);
        return categoryVOList;
    }

    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        //递归获取所有子类别，并组合成为一个“目录树”
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(), categoryVO.getId());
            }
        }
    }
}
