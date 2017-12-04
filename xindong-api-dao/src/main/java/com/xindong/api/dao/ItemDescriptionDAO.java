package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.ItemDescription;

public interface ItemDescriptionDAO {
	Integer insert(ItemDescription record);

    int updateByPrimaryKey(ItemDescription record);

    ItemDescription selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<ItemDescription> selectByItemId(Integer itemId);

}