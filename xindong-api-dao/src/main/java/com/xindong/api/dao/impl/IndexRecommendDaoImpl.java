package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.IndexRecommendDao;
import com.xindong.api.domain.IndexRecommend;
@Repository(value="indexRecommendDao")
@SuppressWarnings("unchecked")
public class IndexRecommendDaoImpl extends SqlMapClientTemplate implements IndexRecommendDao {

    public IndexRecommendDaoImpl() {
        super();
    }

    public int insert(IndexRecommend record) {
        return (Integer)insert("index_recommend.insert", record);
    }

    public int updateByPrimaryKey(IndexRecommend record) {
        int rows = update("index_recommend.updateByPrimaryKey", record);
        return rows;
    }


    public IndexRecommend selectByPrimaryKey(Integer id) {
        IndexRecommend key = new IndexRecommend();
        key.setId(id);
        IndexRecommend record = (IndexRecommend) queryForObject("index_recommend.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        IndexRecommend key = new IndexRecommend();
        key.setId(id);
        int rows = delete("index_recommend.deleteByPrimaryKey", key);
        return rows;
    }

	
	@Override
	public List<IndexRecommend> selectByCondition(IndexRecommend indexRecommend) {
		return queryForList("index_recommend.selectByCondition", indexRecommend);
	}


   
}