package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.ItemTourFlag;

public interface ItemTourFlagDAO {
	Integer insert(ItemTourFlag record);

    int updateByPrimaryKey(ItemTourFlag record);

    ItemTourFlag selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<ItemTourFlag> selectByCondition(ItemTourFlag flag);

}