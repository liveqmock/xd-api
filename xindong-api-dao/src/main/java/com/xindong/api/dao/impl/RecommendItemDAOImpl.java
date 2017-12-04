package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.RecommendItemDAO;
import com.xindong.api.domain.RecommendItem;
@Repository(value="recommendItemDao")
public class RecommendItemDAOImpl extends SqlMapClientTemplate implements RecommendItemDAO {

    public RecommendItemDAOImpl() {
        super();
    }

    public Integer insert(RecommendItem record) {
    	return (Integer) insert("recommend_item.insert", record);
    }

    public int updateByPrimaryKey(RecommendItem record) {
        int rows = update("recommend_item.updateByPrimaryKey", record);
        return rows;
    }


    public RecommendItem selectByPrimaryKey(Integer recommendItemId) {
        RecommendItem key = new RecommendItem();
        key.setRecommendItemId(recommendItemId);
        RecommendItem record = (RecommendItem) queryForObject("recommend_item.selectByPrimaryKey", key);
        return record;
    }

	@Override
	public int deleteByPrimaryKey(Integer recommendItemId) {
		int rows = delete("recommend_item.deleteByPrimaryKey", recommendItemId);
        return rows;
	}

   

    
}