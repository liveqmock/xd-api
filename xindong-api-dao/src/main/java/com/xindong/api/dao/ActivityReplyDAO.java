package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.ActivityReply;
import com.xindong.api.domain.query.ActivityReplyQuery;
import com.xindong.api.domain.vo.ActivityReplyVO;

public interface ActivityReplyDAO {
	Integer insert(ActivityReply record);

    int updateByPrimaryKey(ActivityReply record);

    ActivityReply selectByPrimaryKey(Integer activityReplyId);

    int deleteByPrimaryKey(Integer activityReplyId);

	int countByCondition(ActivityReplyQuery activityReplyQuery);

	List<ActivityReply> selectByConditionForPage(ActivityReplyQuery activityReplyQuery);
	List<ActivityReplyVO> selectByReplyForPage(ActivityReplyQuery activityReplyQuery);


}