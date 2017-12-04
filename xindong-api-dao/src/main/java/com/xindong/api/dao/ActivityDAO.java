package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.Activity;
import com.xindong.api.domain.query.ActivityQuery;


public interface ActivityDAO {
	Integer insert(Activity record);

    int updateByPrimaryKey(Activity record);

    Activity selectByPrimaryKey(Integer activityId);

    int deleteByPrimaryKey(Integer activityId);

	int countByCondition(ActivityQuery activityQuery);

	List<Activity> selectByConditionForPage(ActivityQuery activityQuery);
	
	List<Activity> selectByCondition(ActivityQuery activityQuery);
}