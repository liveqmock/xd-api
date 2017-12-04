package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.ItemVideo;
import com.xindong.api.domain.query.ItemVideoQuery;

public interface ItemVideoDao {
	
	int countByCondition(ItemVideoQuery itemVideoQuery);
	
	List<ItemVideo> selectByCondition(ItemVideoQuery itemVideoQuery);
	
	List<ItemVideo> selectByConditionForPage(ItemVideoQuery itemVideoQuery);
	
}
