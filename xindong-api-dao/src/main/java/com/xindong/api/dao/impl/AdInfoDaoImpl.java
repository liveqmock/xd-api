package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.AdInfoDao;
import com.xindong.api.domain.AdInfo;
import com.xindong.api.domain.query.AdInfoQuery;
@Repository(value="adInfoDao")
@SuppressWarnings("unchecked")
public class AdInfoDaoImpl extends SqlMapClientTemplate implements AdInfoDao {

    public AdInfoDaoImpl() {
        super();
    }

    public int insert(AdInfo record) {
    	int rows =  (Integer) insert("ad_info.insert", record);
    	return rows;
    }

    public int updateByPrimaryKey(AdInfo record) {
        int rows = update("ad_info.updateByPrimaryKey", record);
        return rows;
    }


    public AdInfo selectByPrimaryKey(Integer id) {
        AdInfo key = new AdInfo();
        key.setId(id);
        AdInfo record = (AdInfo) queryForObject("ad_info.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer id) {
        AdInfo key = new AdInfo();
        key.setId(id);
        int rows = delete("ad_info.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public int countByCondition(AdInfoQuery query) {
		return (Integer) queryForObject("ad_info.countByCondition", query);
	}

	@Override
	public List<AdInfo> selectByConditionForPage(AdInfoQuery query) {
		return queryForList("ad_info.selectByConditionForPage", query);
	}

	@Override
	public List<AdInfo> selectByCondition(AdInfoQuery adInfoQuery) {
		return queryForList("ad_info.selectByCondition", adInfoQuery);
	}



}