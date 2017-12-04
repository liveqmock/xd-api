package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.AdImg;

public interface AdImgDao {
    void insert(AdImg record);

    int updateByPrimaryKey(AdImg record);

    AdImg selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<AdImg> selectByCondition(AdImg img);

}