package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ActivityDetailDao;
import com.xindong.api.domain.ActivityDetail;
import com.xindong.api.domain.query.ActivityDetailQuery;

@Repository(value="activityDetailDao")
public class ActivityDetailDaoImpl extends SqlMapClientTemplate implements ActivityDetailDao {

	@Override
	public int countByCondition(ActivityDetailQuery activityDetailQuery) {
		return (Integer) queryForObject("activityDetail.countByCondition", activityDetailQuery);
	}
	
	@Override
	public List<ActivityDetail> selectByCondition(ActivityDetailQuery activityDetailQuery) {
		return (List<ActivityDetail>)queryForList("activityDetail.selectByCondition",activityDetailQuery);
	}

	@Override
	public List<ActivityDetail> selectByConditionForPage(ActivityDetailQuery activityDetailQuery) {
		return (List<ActivityDetail>)queryForList("activityDetail.selectByConditionForPage",activityDetailQuery);
	}

}
