package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.ActivityDetail;
import com.xindong.api.domain.query.ActivityDetailQuery;

public interface ActivityDetailDao {
	
	int countByCondition(ActivityDetailQuery activityDetailQuery);
	
	List<ActivityDetail> selectByCondition(ActivityDetailQuery activityDetailQuery);
	
	List<ActivityDetail> selectByConditionForPage(ActivityDetailQuery activityDetailQuery);
}
