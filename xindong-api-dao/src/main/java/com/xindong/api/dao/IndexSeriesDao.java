package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.IndexSeries;

public interface IndexSeriesDao {
    int insert(IndexSeries record);

    int updateByPrimaryKey(IndexSeries record);

    IndexSeries selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<IndexSeries> selectByCondition(IndexSeries indexSeries);

}