package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.UserInfoIncr;

public interface UserInfoIncrDAO {
    Integer insert(UserInfoIncr record);

    int updateByPrimaryKey(UserInfoIncr record);

    int updateByPrimaryKeySelective(UserInfoIncr record);

    UserInfoIncr selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<UserInfoIncr> selectByCondition(UserInfoIncr incr);

	void updateByCondition(UserInfoIncr incr);

}