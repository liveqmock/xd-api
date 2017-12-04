package com.xindong.api.service;

import java.util.List;

import com.xindong.api.domain.Category;
import com.xindong.api.domain.query.CategoryQuery;
import com.xindong.api.service.result.Result;

public interface CategoryService {
	public List<Category> selectByCondition(CategoryQuery categoryQuery);
	
	/**
	 * 依据分类ID查询分类信息
	 * @param categoryId
	 * @return
	 */
	public Category selectByCategoryId(int categoryId);

	
	public void modify(Category category);

	public Result getAllParentCategory();

}
