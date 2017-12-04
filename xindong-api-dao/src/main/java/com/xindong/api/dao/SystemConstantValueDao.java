package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.SystemConstantValue;


public interface SystemConstantValueDao {
    void insert(SystemConstantValue record);

    int updateByPrimaryKey(SystemConstantValue record);

    int updateByPrimaryKeySelective(SystemConstantValue record);

    SystemConstantValue selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<SystemConstantValue> selectByCondition(SystemConstantValue systemConstantValue);

}