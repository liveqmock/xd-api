package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.IndexImage;

public interface IndexImageDAO {
	Integer insert(IndexImage record);

    int updateByPrimaryKey(IndexImage record);

    IndexImage selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<IndexImage> selectByCondition(IndexImage indexImageQuery);

}