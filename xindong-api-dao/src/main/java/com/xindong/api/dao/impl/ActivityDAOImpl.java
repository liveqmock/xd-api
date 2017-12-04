package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ActivityDAO;
import com.xindong.api.domain.Activity;
import com.xindong.api.domain.query.ActivityQuery;
@Repository(value="activityDao")
@SuppressWarnings("unchecked")
public class ActivityDAOImpl extends SqlMapClientTemplate implements ActivityDAO {

    public ActivityDAOImpl() {
        super();
    }

    public Integer insert(Activity record) {
    	return (Integer) insert("activity.insert", record);
    }


    public int updateByPrimaryKey(Activity record) {
        int rows =update("activity.updateByPrimaryKey", record);
        return rows;
    }


    public Activity selectByPrimaryKey(Integer activityId) {
        Activity key = new Activity();
        key.setActivityId(activityId);
        Activity record = (Activity)queryForObject("activity.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer activityId) {
        Activity key = new Activity();
        key.setActivityId(activityId);
        int rows =delete("activity.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public int countByCondition(ActivityQuery activityQuery) {
		return (Integer)queryForObject("activity.countByCondition", activityQuery);
	}

	
	@Override
	public List<Activity> selectByConditionForPage(ActivityQuery activityQuery) {
		return queryForList("activity.selectByConditionForPage", activityQuery);
	}

	@Override
	public List<Activity> selectByCondition(ActivityQuery activityQuery) {
		return queryForList("activity.selectByCondition", activityQuery);
	}

}