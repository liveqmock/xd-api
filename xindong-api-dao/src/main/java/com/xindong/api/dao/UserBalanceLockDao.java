package com.xindong.api.dao;

import com.xindong.api.domain.UserBalanceLock;

public interface UserBalanceLockDao {
    void insert(UserBalanceLock record);

    int updateByPrimaryKey(UserBalanceLock record);

    UserBalanceLock selectByPrimaryKey(Integer integralLockId);

    int deleteByPrimaryKey(Integer integralLockId);

}