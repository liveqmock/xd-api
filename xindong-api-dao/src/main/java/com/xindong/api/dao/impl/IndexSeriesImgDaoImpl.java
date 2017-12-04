package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.IndexSeriesImgDao;
import com.xindong.api.domain.IndexSeriesImg;
@Repository(value="indexSeriesImgDao")
@SuppressWarnings("unchecked")
public class IndexSeriesImgDaoImpl extends SqlMapClientTemplate implements IndexSeriesImgDao {

    public IndexSeriesImgDaoImpl() {
        super();
    }

    public int insert(IndexSeriesImg record) {
        return (Integer)insert("index_series_img.insert", record);
    }

    public int updateByPrimaryKey(IndexSeriesImg record) {
        int rows = update("index_series_img.updateByPrimaryKey", record);
        return rows;
    }

    public IndexSeriesImg selectByPrimaryKey(Integer id) {
        IndexSeriesImg key = new IndexSeriesImg();
        key.setId(id);
        IndexSeriesImg record = (IndexSeriesImg) queryForObject("index_series_img.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        IndexSeriesImg key = new IndexSeriesImg();
        key.setId(id);
        int rows = delete("index_series_img.deleteByPrimaryKey", key);
        return rows;
    }

	
	@Override
	public List<IndexSeriesImg> selectByCondition(IndexSeriesImg indexSeriesImg) {
		return queryForList("index_series_img.selectByCondition", indexSeriesImg);
	}


   
}