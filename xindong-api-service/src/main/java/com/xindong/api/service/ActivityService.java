package com.xindong.api.service;

import com.xindong.api.domain.ActivityReply;
import com.xindong.api.domain.query.ActivityDetailQuery;
import com.xindong.api.domain.query.ActivityQuery;
import com.xindong.api.domain.query.ActivityReplyQuery;
import com.xindong.api.service.result.Result;

public interface ActivityService {
	public Result getActivityByPage(ActivityQuery activityQuery);

	public Result getActivityDetail(ActivityDetailQuery activityDetailQuery);

	public Result askActivity(ActivityReply activityReply);

	public Result getAskActivityByPage(ActivityReplyQuery activityReplyQuery);

	public Result addActivityCount(Integer id, Integer activityId, Integer type);
	
	public Result getActivityList();
}
