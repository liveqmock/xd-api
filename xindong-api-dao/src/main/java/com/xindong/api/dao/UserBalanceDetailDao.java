package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.UserBalanceDetail;
import com.xindong.api.domain.query.UserBalanceDetailQuery;

public interface UserBalanceDetailDao {
    void insert(UserBalanceDetail record);

    int updateByPrimaryKey(UserBalanceDetail record);

    UserBalanceDetail selectByPrimaryKey(Integer integralDetailId);

    int deleteByPrimaryKey(Integer integralDetailId);

	int countByCondition(UserBalanceDetailQuery detailQuery);

	List<UserBalanceDetail> selectByConditionForPage(UserBalanceDetailQuery detailQuery);

}