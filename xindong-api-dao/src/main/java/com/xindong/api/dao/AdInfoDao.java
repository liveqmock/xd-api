package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.AdInfo;
import com.xindong.api.domain.query.AdInfoQuery;

public interface AdInfoDao {
	int insert(AdInfo record);

    int updateByPrimaryKey(AdInfo record);

    AdInfo selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int countByCondition(AdInfoQuery query);

	List<AdInfo> selectByConditionForPage(AdInfoQuery query);

	List<AdInfo> selectByCondition(AdInfoQuery adInfoQuery);
}