package com.xindong.api.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xindong.api.dao.CategoryDao;
import com.xindong.api.domain.Category;
import com.xindong.api.domain.query.CategoryQuery;
import com.xindong.api.service.CategoryService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;


@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<Category> selectByCondition(CategoryQuery categoryQuery) {
		return categoryDao.selectByCondition(categoryQuery);
	}

	@Override
	public Category selectByCategoryId(int categoryId) {
		return categoryDao.selectByCategoryId(categoryId);
	}

	@Override
	public void modify(Category category) {
		categoryDao.modify(category);
		
	}
	/**
	 * 获取所有一级分类信息
	 */
	@Override
	public Result getAllParentCategory() {
		Result result = new Result();
		try{
			// 查询一级分类
			CategoryQuery categoryQuery = new CategoryQuery();
			categoryQuery.setCategoryLevel(1);
			result.setResult(ComFunction.getCategoryName(913));
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
//			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

}
