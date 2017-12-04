package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.Item;
import com.xindong.api.domain.query.ItemQuery;

public interface ItemDAO {
	Integer insert(Item record);

    int updateByPrimaryKey(Item record);

    Item selectByPrimaryKey(Integer itemId);

    int deleteByPrimaryKey(Integer itemId);

	List<Item> selectByCondition(ItemQuery itemQuery);

	List<Item> selectByConditionForPage(ItemQuery itemQuery);

	List<Item> selectOthersByConditionForPage(ItemQuery itemQuery);

	String selectByskuId(Integer skuId);

}