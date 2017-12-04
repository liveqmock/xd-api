package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.TourDate;

public interface TourDateDAO {
	Integer insert(TourDate record);

    int updateByPrimaryKey(TourDate record);

    TourDate selectByPrimaryKey(Integer dateId);

    int deleteByPrimaryKey(Integer dateId);

	List<TourDate> selectByCondition(TourDate tourDateQuery);

}