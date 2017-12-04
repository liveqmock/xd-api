package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.Category;
import com.xindong.api.domain.query.CategoryQuery;


public interface CategoryDao {
	
	/**
	 * 添加分类信息
	 * @param category
	 * @return
	 */
	public Integer insert(Category category);

	/**
	 * 依据分类ID修改分类信息
	 * @param category
	 */
	public void modify(Category category);

	/**
	 * 依据分类ID查询分类信息
	 * @param categoryId
	 * @return
	 */
	public Category selectByCategoryId(Integer categoryId);
	
	/**
	 * 根据相应的条件查询满足条件的分类信息的总数
	 * @param categoryQuery
	 * @return
	 */
	public int countByCondition(CategoryQuery categoryQuery);
	
	/**
	 * 根据相应的条件查询分类信息
	 * @param categoryQuery
	 * @return
	 */
	public List<Category> selectByCondition(CategoryQuery categoryQuery);
	
	/**
	 * 根据相应的条件查询分类信息---分页查询
	 * @param categoryQuery
	 * @return
	 */
	public List<Category> selectByConditionForPage(CategoryQuery categoryQuery);
}