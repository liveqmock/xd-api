package com.xindong.api.dao;

import com.xindong.api.domain.RecommendItem;

public interface RecommendItemDAO {
	Integer insert(RecommendItem record);

    int updateByPrimaryKey(RecommendItem record);

    RecommendItem selectByPrimaryKey(Integer recommendItemId);

    int deleteByPrimaryKey(Integer recommendItemId);

}