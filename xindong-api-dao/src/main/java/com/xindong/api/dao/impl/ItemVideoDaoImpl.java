package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ItemVideoDao;
import com.xindong.api.domain.ItemVideo;
import com.xindong.api.domain.query.ItemVideoQuery;

@Repository(value="itemVideoDao")
public class ItemVideoDaoImpl extends SqlMapClientTemplate implements ItemVideoDao{

	@Override
	public int countByCondition(ItemVideoQuery itemVideoQuery) {
		return (Integer) queryForObject("itemVideo.countByCondition", itemVideoQuery);
	}
	
	@Override
	public List<ItemVideo> selectByCondition(ItemVideoQuery itemVideoQuery) {
		return queryForList("itemVideo.selectByCondition", itemVideoQuery);
	}
	
	@Override
	public List<ItemVideo> selectByConditionForPage(ItemVideoQuery itemVideoQuery) {
		return queryForList("itemVideo.selectByConditionForPage", itemVideoQuery);
	}

}
