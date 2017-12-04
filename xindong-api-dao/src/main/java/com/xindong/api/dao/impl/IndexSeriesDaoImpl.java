package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.IndexSeriesDao;
import com.xindong.api.domain.IndexSeries;
@Repository(value="indexSeriesDao")
@SuppressWarnings("unchecked")
public class IndexSeriesDaoImpl extends SqlMapClientTemplate implements IndexSeriesDao {

    public IndexSeriesDaoImpl() {
        super();
    }

    public int insert(IndexSeries record) {
       return (Integer) insert("index_series.insert", record);
    }

    public int updateByPrimaryKey(IndexSeries record) {
        int rows = update("index_series.updateByPrimaryKey", record);
        return rows;
    }

    public IndexSeries selectByPrimaryKey(Integer id) {
        IndexSeries key = new IndexSeries();
        key.setId(id);
        IndexSeries record = (IndexSeries) queryForObject("index_series.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        IndexSeries key = new IndexSeries();
        key.setId(id);
        int rows = delete("index_series.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<IndexSeries> selectByCondition(IndexSeries indexSeries) {
		return queryForList("index_series.selectByCondition", indexSeries);
	}


   
}