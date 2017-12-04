package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.IndexImageDAO;
import com.xindong.api.domain.IndexImage;
@Repository(value="indexImageDao")
@SuppressWarnings("unchecked")
public class IndexImageDAOImpl extends SqlMapClientTemplate implements IndexImageDAO {

    public IndexImageDAOImpl() {
        super();
    }

    public Integer insert(IndexImage record) {
    	return (Integer) insert("index_image.insert", record);
    }

    public int updateByPrimaryKey(IndexImage record) {
        int rows = update("index_image.updateByPrimaryKey", record);
        return rows;
    }


    public IndexImage selectByPrimaryKey(Integer id) {
        IndexImage key = new IndexImage();
        key.setId(id);
        IndexImage record = (IndexImage) queryForObject("index_image.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer id) {
        IndexImage key = new IndexImage();
        key.setId(id);
        int rows = delete("index_image.deleteByPrimaryKey", key);
        return rows;
    }

	
	@Override
	public List<IndexImage> selectByCondition(IndexImage indexImageQuery) {
		return queryForList("index_image.selectByCondition",indexImageQuery);
	}



}