package com.xindong.api.dao;

import com.xindong.api.domain.UserBalance;


public interface UserBalanceDao {
    void insert(UserBalance record);

    int updateByPrimaryKey(UserBalance record);

    UserBalance selectByPrimaryKey(Integer integralId);

    int deleteByPrimaryKey(Integer integralId);

	UserBalance selectByUserId(Integer userId);

	int updateByBalance(UserBalance useintegral);

}