package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.IndexRecommend;

public interface IndexRecommendDao {
    int insert(IndexRecommend record);

    int updateByPrimaryKey(IndexRecommend record);

    IndexRecommend selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<IndexRecommend> selectByCondition(IndexRecommend indexRecommend);

}