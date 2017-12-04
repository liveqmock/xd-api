package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.ItemImage;

public interface ItemImageDAO {
	Integer insert(ItemImage record);

    int updateByPrimaryKey(ItemImage record);

    ItemImage selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

//	List<ItemImage> selectByItemId(Integer itemId);

	List<ItemImage> selectByCondition(ItemImage itemq);
	
	List<ItemImage> selectByConditionForH5(ItemImage itemq);
}