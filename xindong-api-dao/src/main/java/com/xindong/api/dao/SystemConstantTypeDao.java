package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.SystemConstantType;


public interface SystemConstantTypeDao {
    void insert(SystemConstantType record);

    int updateByPrimaryKey(SystemConstantType record);

    int updateByPrimaryKeySelective(SystemConstantType record);

    SystemConstantType selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<SystemConstantType> selectByCondition(SystemConstantType systemConstantType);

}