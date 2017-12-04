package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.IndexSeriesImg;

public interface IndexSeriesImgDao {
    int insert(IndexSeriesImg record);

    int updateByPrimaryKey(IndexSeriesImg record);

    IndexSeriesImg selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<IndexSeriesImg> selectByCondition(IndexSeriesImg indexSeriesImg);

}