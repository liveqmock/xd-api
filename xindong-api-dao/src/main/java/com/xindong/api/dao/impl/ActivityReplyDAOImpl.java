package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ActivityReplyDAO;
import com.xindong.api.domain.ActivityReply;
import com.xindong.api.domain.query.ActivityReplyQuery;
import com.xindong.api.domain.vo.ActivityReplyVO;
@Repository(value="activityReplyDao")
@SuppressWarnings("unchecked")
public class ActivityReplyDAOImpl extends SqlMapClientTemplate implements ActivityReplyDAO {

    public ActivityReplyDAOImpl() {
        super();
    }

    public Integer insert(ActivityReply record) {
    	return (Integer) insert("activity_reply.insert", record);
    }

    public int updateByPrimaryKey(ActivityReply record) {
        int rows = update("activity_reply.updateByPrimaryKey", record);
        return rows;
    }


    public ActivityReply selectByPrimaryKey(Integer activityReplyId) {
        ActivityReply key = new ActivityReply();
        key.setActivityReplyId(activityReplyId);
        ActivityReply record = (ActivityReply) queryForObject("activity_reply.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer activityReplyId) {
        ActivityReply key = new ActivityReply();
        key.setActivityReplyId(activityReplyId);
        int rows = delete("activity_reply.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public int countByCondition(ActivityReplyQuery activityReplyQuery) {
		return  (Integer) queryForObject("activity_reply.countByCondition", activityReplyQuery);
	}

	
	@Override
	public List<ActivityReply> selectByConditionForPage(ActivityReplyQuery activityReplyQuery) {
		return (List<ActivityReply>) queryForList("activity_reply.selectByConditionForPage", activityReplyQuery);
	}

	@Override
	public List<ActivityReplyVO> selectByReplyForPage(ActivityReplyQuery activityReplyQuery) {
		return (List<ActivityReplyVO>) queryForList("activity_reply.selectByReplyForPage", activityReplyQuery);
	}


}