package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.TourFlag;

public interface TourFlagDAO {
	Integer insert(TourFlag record);

    int updateByPrimaryKey(TourFlag record);

    TourFlag selectByPrimaryKey(Integer flagId);

    int deleteByPrimaryKey(Integer flagId);
    List<TourFlag> selectByCondition(TourFlag tourFlagQuery);
}