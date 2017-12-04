package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.TourOrigin;

public interface TourOriginDAO {
	Integer insert(TourOrigin record);

    int updateByPrimaryKey(TourOrigin record);

    TourOrigin selectByPrimaryKey(Integer originId);

    int deleteByPrimaryKey(Integer originId);
    List<TourOrigin> selectByCondition(TourOrigin tourOriginQuery);

}